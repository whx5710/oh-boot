package com.finn.support.service.impl;

import com.finn.core.cache.RedisCache;
import com.finn.core.cache.RedisKeys;
import com.finn.core.constant.Constant;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.HttpContextUtils;
import com.finn.core.utils.IpUtils;
import com.finn.core.utils.Tools;
import com.finn.framework.common.properties.MultiTenantProperties;
import com.finn.framework.common.properties.SecurityProperties;
import com.finn.framework.security.cache.TokenStoreCache;
import com.finn.framework.security.mobile.MobileAuthenticationToken;
import com.finn.framework.security.user.RefreshTokenInfo;
import com.finn.framework.security.user.UserDetail;
import com.finn.support.cache.TenantCache;
import com.finn.support.entity.UserEntity;
import com.finn.support.service.LogLoginService;
import com.finn.support.service.UserService;
import com.finn.support.enums.LoginOperationEnum;
import com.finn.support.service.AuthService;
import com.finn.support.service.CaptchaService;
import com.finn.support.vo.AccountLoginVO;
import com.finn.support.vo.MobileLoginVO;
import com.finn.support.vo.TokenVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 权限认证服务
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class AuthServiceImpl implements AuthService {
    private final CaptchaService captchaService;
    private final TokenStoreCache tokenStoreCache;
    private final AuthenticationManager authenticationManager;
    private final LogLoginService logLoginService;
    private final UserService userService;

    private final RedisCache redisCache;
    private final TenantCache tenantCache;

    private final SecurityProperties securityProperties;
    private final MultiTenantProperties tenantProperties;

    public AuthServiceImpl(CaptchaService captchaService, TokenStoreCache tokenStoreCache,
                           AuthenticationManager authenticationManager, LogLoginService logLoginService,
                           UserService userService, RedisCache redisCache,
                           SecurityProperties securityProperties,
                           TenantCache tenantCache, MultiTenantProperties tenantProperties) {
        this.captchaService = captchaService;
        this.tokenStoreCache = tokenStoreCache;
        this.authenticationManager = authenticationManager;
        this.logLoginService = logLoginService;
        this.userService = userService;
        this.redisCache = redisCache;
        this.securityProperties = securityProperties;
        this.tenantCache = tenantCache;
        this.tenantProperties = tenantProperties;
    }

    /**
     * 用户名、密码登录
     * @param login 登录信息
     * @return token信息
     */
    @Override
    public TokenVO loginByAccount(AccountLoginVO login) {
        AssertUtils.isBlank(login.getUsername(), "用户名");
        String msg = "";
        // 验证码效验
        boolean flag = captchaService.validate(login.getKey(), login.getCaptcha());
        if (!flag) {
            // 保存登录日志
            logLoginService.save(login.getUsername(), Constant.FAIL, LoginOperationEnum.CAPTCHA_FAIL.getValue(), null);
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
    public TokenVO loginByUserKey(AccountLoginVO login) {
        String userKey = login.getUserKey();
        AssertUtils.isBlank(userKey, "用户密钥");
        return createToken(login, true);
    }

    @Override
    public TokenVO loginByMobile(MobileLoginVO login) {
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
        String accessToken = Tools.generator();

        String refreshToken = Tools.generator();

        // 保存用户信息到缓存
        tokenStoreCache.saveUser(accessToken, refreshToken, user);

        return new TokenVO(accessToken, refreshToken);
    }

    /**
     * 刷新token<br/>
     * refreshToken 不重新生成，防止泄露可无限生成token
     * @param refreshToken 刷新token
     * @param request 请求
     * @return token
     */
    @Override
    public TokenVO refreshToken(String refreshToken, HttpServletRequest request) {
        String refreshKey = RedisKeys.getAccessRefreshTokenKey(refreshToken);
        if(redisCache.hasKey(refreshKey)){
            RefreshTokenInfo userDetail = (RefreshTokenInfo) redisCache.get(refreshKey);
            String ip = IpUtils.getIpAddress(request);
            // 获取刷新token有效时间
            Long expire = redisCache.getExpire(refreshKey);
            // 请求IP相同，且刷新token有效时长大于1秒才重新生成token
            if(userDetail.getIp().equals(ip) && expire > 1){
                // 重新查询用户信息
                UserEntity userEntity = userService.getUser(userDetail.getId());
                UserDetail userDetailDb = (UserDetail) userService.getUserDetails(userEntity);
                userDetailDb.setLoginTime(LocalDateTime.now());
                userDetailDb.setIp(ip);
                // userDetailDb.setRefreshTokenExpire(securityProperties.getRefreshTokenExpire());
                // 防止无限可刷新token，此处使用旧的刷新token有效时间
                userDetailDb.setRefreshTokenExpire(expire);
                // 生成 accessToken
                String accessToken = Tools.generator();
                // 保存用户信息到缓存
                tokenStoreCache.saveUser(accessToken, refreshToken, userDetailDb);
                return new TokenVO(accessToken, refreshToken);
            }else{
                throw new ServerException("【IP】请求非法，刷新token失败");
            }
        }else{
            throw new ServerException("刷新token过期，请重新登录");
        }
    }

    @Override
    public void logout(String accessToken, String refreshToken) {
        // 用户信息
        UserDetail user = tokenStoreCache.getUser(accessToken);

        // 删除用户信息
        tokenStoreCache.deleteUser(accessToken);
        // 删除刷新token
        if(refreshToken != null && !refreshToken.isEmpty()){
            if(redisCache.hasKey(RedisKeys.getAccessRefreshTokenKey(refreshToken))){
                redisCache.delete(RedisKeys.getAccessRefreshTokenKey(refreshToken));
            }
        }
        // 保存登录日志
        logLoginService.save(user.getUsername(), Constant.SUCCESS, LoginOperationEnum.LOGOUT_SUCCESS.getValue(), user.getTenantId());
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
    private TokenVO createToken(AccountLoginVO login, boolean checkKey){
        String msg;
        Authentication authentication;
        String authCountKey = RedisKeys.getAuthCountKey(login.getUsername());
        // 判断错误次数，超出则锁定账号
        checkLock(authCountKey);
        try {
            // 用户认证,调用authenticate对认证请求进行处理，入参是一个Authentication对象,封装了用户名和密码
            // 返回一个已经被认证过的Authentication对象
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
        checkKey(checkKey, user.getUserKey(), login);
        // 租户判断
        if(tenantProperties.isEnable() && user.getTenantId() != null && !user.getTenantId().isEmpty()
                && !tenantCache.valid(user.getTenantId())){
            // 登录失败计数
            int authCount =loginCount(login.getUsername());
            if(authCount > 0){
                if((securityProperties.getAuthCount() - authCount) > 0){
                    msg = "租户无效，" + (securityProperties.getAuthCount() - authCount) + "次失败后锁定账号";
                }else{
                    msg = "租户无效，账号即将锁定";
                }
            }else{
                msg = "租户无效，登录失败";
            }
            throw new ServerException(msg);
        }

        // 登录时间和token刷新时间
        user.setLoginTime(LocalDateTime.now());
        user.setRefreshTokenExpire(securityProperties.getRefreshTokenExpire());
        // 生成 accessToken
        String accessToken = Tools.generator();
        String refreshToken = Tools.generator();
        // IP
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String ip = IpUtils.getIpAddress(request);
        user.setIp(ip);
        // 保存用户信息到缓存
        tokenStoreCache.saveUser(accessToken, refreshToken, user);
        // 限制次数内登录成功，清除错误计数
        redisCache.delete(authCountKey);
        return new TokenVO(accessToken, refreshToken);
    }

    /**
     * 检查是否锁定账号
     * @param authCountKey redis key
     */
    private void checkLock(String authCountKey){
        String msg = "";
        boolean authLockFlag = securityProperties.getAuthCount() > 0;
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
    }

    /**
     * 校验用户密钥
     * @param checkKey 是否校验
     * @param userKey 用户密钥
     * @param login 登录参数
     */
    private void checkKey(boolean checkKey,String userKey, AccountLoginVO login){
        String msg = "";
        if (checkKey && !login.getUserKey().equals(userKey)) {
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
    }
}
