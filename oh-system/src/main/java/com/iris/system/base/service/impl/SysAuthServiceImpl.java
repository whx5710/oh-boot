package com.iris.system.base.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.iris.framework.common.cache.RedisCache;
import com.iris.framework.common.cache.RedisKeys;
import com.iris.framework.common.config.properties.SecurityProperties;
import com.iris.framework.common.utils.AssertUtils;
import com.iris.framework.common.utils.HttpContextUtils;
import com.iris.framework.common.utils.IpUtils;
import com.iris.framework.common.utils.IrisTools;
import com.iris.system.base.enums.LoginOperationEnum;
import com.iris.system.base.vo.SysAccountLoginVO;
import com.iris.system.base.vo.SysMobileLoginVO;
import com.iris.system.base.vo.SysTokenVO;
import com.iris.system.base.vo.SysUserVO;
import com.iris.system.sms.service.SmsApi;
import com.iris.system.base.service.SysAuthService;
import com.iris.system.base.service.SysCaptchaService;
import com.iris.system.base.service.SysLogLoginService;
import com.iris.system.base.service.SysUserService;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.security.cache.TokenStoreCache;
import com.iris.framework.security.mobile.MobileAuthenticationToken;
import com.iris.framework.security.user.UserDetail;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 权限认证服务
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class SysAuthServiceImpl implements SysAuthService {
    private final SysCaptchaService sysCaptchaService;
    private final TokenStoreCache tokenStoreCache;
    private final AuthenticationManager authenticationManager;
    private final SysLogLoginService sysLogLoginService;
    private final SysUserService sysUserService;
    private final SmsApi smsApi;

    private final RedisCache redisCache;

    private final SecurityProperties securityProperties;
    /**
     * 刷新令牌过期时间，单位：秒
     * 1天
     */
    @Value("${oh.security.access-token-expire:259200}")
    public int refreshTokenExpire = 60 * 60 * 24;

    public SysAuthServiceImpl(SysCaptchaService sysCaptchaService, TokenStoreCache tokenStoreCache,
                              AuthenticationManager authenticationManager, SysLogLoginService sysLogLoginService,
                              SysUserService sysUserService, SmsApi smsApi, RedisCache redisCache,
                              SecurityProperties securityProperties) {
        this.sysCaptchaService = sysCaptchaService;
        this.tokenStoreCache = tokenStoreCache;
        this.authenticationManager = authenticationManager;
        this.sysLogLoginService = sysLogLoginService;
        this.sysUserService = sysUserService;
        this.smsApi = smsApi;
        this.redisCache = redisCache;
        this.securityProperties = securityProperties;
    }

    /**
     * 用户名、密码登录
     * @param login 登录信息
     * @return token信息
     */
    @Override
    public SysTokenVO loginByAccount(SysAccountLoginVO login) {
        AssertUtils.isBlank(login.getUsername(), "用户名");
        String msg = "";
        // 验证码效验
        boolean flag = sysCaptchaService.validate(login.getKey(), login.getCaptcha());
        if (!flag) {
            // 保存登录日志
            sysLogLoginService.save(login.getUsername(), Constant.FAIL, LoginOperationEnum.CAPTCHA_FAIL.getValue());
            // 登录失败计数
            int authCount = loginCount(login.getUsername());
            if(authCount > 0 && (securityProperties.getAuthCount() - authCount) > 0){
                msg = "验证码错误，" + (securityProperties.getAuthCount() - authCount) + "次失败后锁定账号";
            }else{
                msg = "验证码错误";
            }
            throw new ServerException(msg);
        }
        // 验证账号生成token
        return createToken(login, false);
    }

    /**
     * 第三方用户登录（验证码不校验，密钥必填）
     * @param login 登录信息，用户密钥必填
     * @return token
     */
    @Override
    public SysTokenVO loginByUserKey(SysAccountLoginVO login) {
        String userKey = login.getUserKey();
        AssertUtils.isBlank(userKey, "用户密钥");
        return createToken(login, true);
    }

    @Override
    public SysTokenVO loginByMobile(SysMobileLoginVO login) {
        Authentication authentication;
        try {
            // 用户认证
            authentication = authenticationManager.authenticate(
                    new MobileAuthenticationToken(login.getMobile(), login.getCode()));
        } catch (BadCredentialsException e) {
            throw new ServerException("手机号或验证码错误");
        }

        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();

        // 生成 accessToken
        String accessToken = IrisTools.generator();

        String refreshToken = IrisTools.generator();

        // 保存用户信息到缓存
        tokenStoreCache.saveUser(accessToken, user);

        return new SysTokenVO(accessToken, refreshToken);
    }

    @Override
    public boolean sendCode(String mobile) {
        // 生成6位验证码
        String code = RandomUtil.randomNumbers(6);

        SysUserVO user = sysUserService.getByMobile(mobile);
        if (user == null) {
            throw new ServerException("手机号未注册");
        }

        // 发送短信
        return smsApi.sendCode(mobile, "code", code);
    }

    @Override
    public void logout(String accessToken) {
        // 用户信息
        UserDetail user = tokenStoreCache.getUser(accessToken);

        // 删除用户信息
        tokenStoreCache.deleteUser(accessToken);

        // 保存登录日志
        sysLogLoginService.save(user.getUsername(), Constant.SUCCESS, LoginOperationEnum.LOGOUT_SUCCESS.getValue());
    }

    /**
     * 登录失败计数
     * @param loginName 用户名
     */
    private int loginCount(String loginName){
        // 是否开启账号锁定
        boolean authLockFlag = securityProperties.getAuthCount() > 0;
        String authCountKey = RedisKeys.getAuthCountKey(loginName);
        int authCount = 0;
        if(authLockFlag){
            if(redisCache.hasKey(authCountKey)){
                authCount = (int) redisCache.get(authCountKey);
                authCount++;
            }else{
                authCount = 1;
            }
            redisCache.set(authCountKey, authCount, securityProperties.getLockTime());
        }
        return authCount;
    }

    /**
     * 验证账号生成token信息
     * 1、判断账号是否锁定
     * 2、验证账号密码(密钥)
     * 3、生成token
     * 4、缓存到redis
     * @param login login 账号参数
     * @param checkKey 是否检查用户密钥
     * @return token
     */
    private SysTokenVO createToken(SysAccountLoginVO login, boolean checkKey){
        String msg;
        Authentication authentication;
        // 判断错误次数，超出则锁定账号
        boolean authLockFlag = securityProperties.getAuthCount() > 0;
        String authCountKey = RedisKeys.getAuthCountKey(login.getUsername());
        if(authLockFlag && redisCache.hasKey(authCountKey)){
            // 错误次数
            int authCount = (int) redisCache.get(authCountKey);
            if(authCount >= securityProperties.getAuthCount()){
                // 锁定时间（秒）
                Long lockTime = redisCache.getExpire(authCountKey);
                long time = 1;
                if(lockTime > 3600){
                    time = lockTime/3600;
                    msg = "账号已被锁定，请" + time + "小时后再试！";
                }else{
                    time = lockTime/60;
                    msg = "账号已被锁定，请" + (time==0?1:time) + "分钟后再试！";
                }
                throw new ServerException(msg);
            }
        }
        try {
            // 用户认证
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        } catch (BadCredentialsException e) {
            // 登录失败计数
            int authCount =loginCount(login.getUsername());
            if(authCount > 0 ){
                if((securityProperties.getAuthCount() - authCount) > 0){
                    msg = "用户名或密码错误，" + (securityProperties.getAuthCount() - authCount) + "次失败后锁定账号";
                }else{
                    msg = "用户名或密码错误，账号即将锁定";
                }
            }else{
                msg = "用户名或密码错误";
            }
            throw new ServerException(msg);
        }
        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();
        // 判断用户密钥
        if (checkKey && !login.getUserKey().equals(user.getUserKey())) {
            // 登录失败计数
            int authCount =loginCount(login.getUsername());
            if(authCount > 0){
                if((securityProperties.getAuthCount() - authCount) > 0){
                    msg = "用户密钥错误，" + (securityProperties.getAuthCount() - authCount) + "次失败后锁定账号";
                }else{
                    msg = "用户密钥错误，账号即将锁定";
                }
            }else{
                msg = "用户密钥错误";
            }
            throw new ServerException(msg);
        }
        // 登录时间和token刷新时间
        long date = System.currentTimeMillis();
        user.setLoginTime(date);
        user.setRefreshTokenExpire(DateUtil.offsetSecond(new Date(date), refreshTokenExpire).getTime());
        // 生成 accessToken
        String accessToken = IrisTools.generator();
        String refreshToken = IrisTools.generator();
        // IP
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String ip = IpUtils.getIpAddr(request);
        user.setIp(ip);
        // 保存用户信息到缓存
        tokenStoreCache.saveUser(accessToken, user);
        // 限制次数内登录成功，清除错误计数
        redisCache.delete(authCountKey);
        return new SysTokenVO(accessToken, refreshToken);
    }
}
