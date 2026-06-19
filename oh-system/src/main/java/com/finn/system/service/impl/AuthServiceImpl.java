package com.finn.system.service.impl;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.cache.RedisKeys;
import com.finn.framework.common.constant.Constant;
import com.finn.framework.exception.ServerException;
import com.finn.framework.security.wechat.WechatMiniAuthenticationToken;
import com.finn.framework.utils.AssertUtils;
import com.finn.framework.utils.HttpContextUtils;
import com.finn.framework.utils.IpUtils;
import com.finn.framework.utils.Tools;
import com.finn.framework.common.properties.SecurityProperties;
import com.finn.framework.cache.TokenCache;
import com.finn.framework.security.mobile.MobileAuthenticationToken;
import com.finn.framework.security.user.RefreshTokenInfo;
import com.finn.framework.security.user.UserDetail;
import com.finn.system.entity.UserEntity;
import com.finn.system.enums.LoginOperationEnum;
import com.finn.system.service.AuthService;
import com.finn.system.service.CaptchaService;
import com.finn.system.service.LoginLogService;
import com.finn.system.service.UserService;
import com.finn.system.vo.AccountLoginVO;
import com.finn.system.vo.MobileLoginVO;
import com.finn.system.vo.TokenVO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.finn.framework.common.enums.ErrorCode.REFRESH_TOKEN_ERROR;

/**
 * 权限认证服务
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class AuthServiceImpl implements AuthService {
    private final CaptchaService captchaService;
    private final TokenCache tokenCache;
    private final AuthenticationManager authenticationManager;
    private final LoginLogService loginLogService;
    private final UserService userService;

    private final RedisCache redisCache;

    private final SecurityProperties securityProperties;

    private final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(CaptchaService captchaService, TokenCache tokenCache,
                           AuthenticationManager authenticationManager, LoginLogService loginLogService,
                           UserService userService, RedisCache redisCache,
                           SecurityProperties securityProperties) {
        this.captchaService = captchaService;
        this.tokenCache = tokenCache;
        this.authenticationManager = authenticationManager;
        this.loginLogService = loginLogService;
        this.userService = userService;
        this.redisCache = redisCache;
        this.securityProperties = securityProperties;
    }

    /**
     * 用户名、密码登录
     * @param login 登录信息
     * @return token信息
     */
    @Override
    public TokenVO loginByAccount(AccountLoginVO login) {
        AssertUtils.isBlank(login.getUsername(), "用户名");
        // 验证码效验
        boolean flag = captchaService.validate(login.getPlatformKey(), login.getKey(), login.getCaptcha());
        if (!flag) {
            // 保存登录日志
            loginLogService.save(login.getUsername(), Constant.FAIL, LoginOperationEnum.CAPTCHA_FAIL.getValue());
            throw new ServerException(buildErrorMessage(login.getUsername(), "验证码错误"));
        }
        // 验证账号生成token
        return createToken(login, false);
    }

    /**
     * 微信登录，小程序调用 wx.login() 获取 code
     * @param code 微信临时登录凭证
     * @return
     */
    @Override
    public TokenVO wechatLogin(String code) {
        // 1. 创建未认证 Token
        WechatMiniAuthenticationToken authRequest = new WechatMiniAuthenticationToken(code);
        // 2. 执行认证
        Authentication authentication;
        try {
            // 调用 WechatMiniAuthenticationProvider.authenticate()，获取用户信息
            authentication = authenticationManager.authenticate(authRequest);
        }catch (Exception e){
            log.error("微信认证失败！code = {}, {}", code, e.getMessage());
            throw new ServerException("微信认证失败！");
        }

        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();
        if(user == null){
            throw new ServerException("未获取到用户信息");
        }
        if(user.getStatus() == 0){
            throw new ServerException("用户已被禁用!");
        }
        // 登录时间和token刷新时间
        user.setLoginTime(LocalDateTime.now());
        Long refreshTokenExpire = securityProperties.getRefreshTokenExpire() * 365; // 微信小程序的刷新token可设置长一点，或者可另外配置
        user.setRefreshTokenExpire(refreshTokenExpire);

        // 生成 accessToken
        String accessToken = Tools.generator();

        String refreshToken = Tools.generator();

        // 保存用户信息到缓存
        tokenCache.saveUser(accessToken, refreshToken, user);

        return new TokenVO(accessToken, refreshToken, securityProperties.getAccessTokenExpire(), refreshTokenExpire);
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
            // 执行认证
            authentication = authenticationManager.authenticate(
                    new MobileAuthenticationToken(login.getMobile(), login.getCode(), login.getUserType()));
        } catch (BadCredentialsException e) {
            throw new ServerException("手机号或验证码错误");
        }

        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();
        if(user == null){
            throw new ServerException("未获取到用户信息");
        }
        if(user.getStatus() == 0){
            throw new ServerException("用户已被禁用!");
        }
        // 登录时间和token刷新时间
        user.setLoginTime(LocalDateTime.now());
        user.setRefreshTokenExpire(securityProperties.getRefreshTokenExpire());

        // 生成 accessToken
        String accessToken = Tools.generator();

        String refreshToken = Tools.generator();

        // 保存用户信息到缓存
        tokenCache.saveUser(accessToken, refreshToken, user);

        return new TokenVO(accessToken, refreshToken, securityProperties.getAccessTokenExpire(), securityProperties.getRefreshTokenExpire());
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
                if(userEntity.getStatus() == 0){
                    throw new ServerException("用户已被禁用!");
                }
                UserDetail userDetailDb = (UserDetail) userService.getUserDetails(userEntity);
                userDetailDb.setLoginTime(LocalDateTime.now());
                userDetailDb.setIp(ip);
                // userDetailDb.setRefreshTokenExpire(securityProperties.getRefreshTokenExpire());
                // 防止无限可刷新token，此处使用旧的刷新token有效时间
                userDetailDb.setRefreshTokenExpire(expire);
                // 生成 accessToken
                String accessToken = Tools.generator();
                // 保存用户信息到缓存
                tokenCache.saveUser(accessToken, refreshToken, userDetailDb);
                return new TokenVO(accessToken, refreshToken, securityProperties.getAccessTokenExpire(), securityProperties.getRefreshTokenExpire());
            }else{
                throw new ServerException("【IP】请求非法，刷新token失败");
            }
        }else{
            // 刷新token过期，请重新登录
            throw new ServerException(REFRESH_TOKEN_ERROR);
        }
    }

    @Override
    public void logout(String accessToken, String refreshToken) {
        // 删除刷新token
        if(refreshToken != null && !refreshToken.isEmpty()){
            if(redisCache.hasKey(RedisKeys.getAccessRefreshTokenKey(refreshToken))){
                redisCache.delete(RedisKeys.getAccessRefreshTokenKey(refreshToken));
            }
        }
        // 用户信息
        UserDetail user = tokenCache.getUser(accessToken);
        if(user != null){
            // 删除用户信息
            tokenCache.deleteUser(user.getId(), accessToken);
            // 保存登录日志
            loginLogService.save(user.getUsername(), Constant.SUCCESS, LoginOperationEnum.LOGOUT_SUCCESS.getValue());
        }
    }

    /**
     * 登录失败计数
     * @param loginName 用户名
     */
    private int incrementLoginCount(String loginName) {
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
            throw new ServerException(buildErrorMessage(login.getUsername(), "用户名或密码错误"));
        } catch (InternalAuthenticationServiceException e){
            log.error("登录发生异常!{}", e.getMessage());
            throw new ServerException("登录发生异常，请联系管理员!");
        }
        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();
        if(user == null){
            throw new ServerException("未获取到用户信息!");
        }
        if(user.getStatus() == 0){
            throw new ServerException("用户已被禁用!");
        }
        // 判断用户密钥
        checkKey(checkKey, user.getUserKey(), login);

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
        tokenCache.saveUser(accessToken, refreshToken, user);
        // 限制次数内登录成功，清除错误计数
        redisCache.delete(authCountKey);
        return new TokenVO(accessToken, refreshToken, securityProperties.getAccessTokenExpire(), securityProperties.getRefreshTokenExpire());
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
        if (checkKey && !login.getUserKey().equals(userKey)) {
            throw new ServerException(buildErrorMessage(login.getUsername(), "用户密钥错误"));
        }
    }

    /**
     * 统计错误次数
     * @param userName 用户名
     * @param errMsg 错误提示
     */
    private String buildErrorMessage(String userName, String errMsg) {
        int authCount = incrementLoginCount(userName);
        if(authCount > 0 ){
            if((securityProperties.getAuthCount() - authCount) > 0){
                return errMsg + "，" + (securityProperties.getAuthCount() - authCount) + "次失败后锁定账号";
            }else{
                return errMsg + "，账号即将锁定";
            }
        }
        return errMsg;
    }
}
