package com.iris.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.iris.sms.service.SmsApi;
import com.iris.system.service.*;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.security.cache.TokenStoreCache;
import com.iris.framework.security.mobile.MobileAuthenticationToken;
import com.iris.framework.security.user.UserDetail;
import com.iris.framework.security.utils.TokenUtils;
import com.iris.system.enums.LoginOperationEnum;
import com.iris.system.vo.SysAccountLoginVO;
import com.iris.system.vo.SysMobileLoginVO;
import com.iris.system.vo.SysTokenVO;
import com.iris.system.vo.SysUserVO;
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
    /**
     * 刷新令牌过期时间，单位：秒
     * 3天
     */
    @Value("${oh.security.access-token-expire:259200}")
    public int refreshTokenExpire = 60 * 60 * 24 * 3;

    public SysAuthServiceImpl(SysCaptchaService sysCaptchaService, TokenStoreCache tokenStoreCache, AuthenticationManager authenticationManager, SysLogLoginService sysLogLoginService, SysUserService sysUserService, SmsApi smsApi) {
        this.sysCaptchaService = sysCaptchaService;
        this.tokenStoreCache = tokenStoreCache;
        this.authenticationManager = authenticationManager;
        this.sysLogLoginService = sysLogLoginService;
        this.sysUserService = sysUserService;
        this.smsApi = smsApi;
    }

    @Override
    public SysTokenVO loginByAccount(SysAccountLoginVO login) {
        // 验证码效验
        boolean flag = sysCaptchaService.validate(login.getKey(), login.getCaptcha());
        if (!flag) {
            // 保存登录日志
            sysLogLoginService.save(login.getUsername(), Constant.FAIL, LoginOperationEnum.CAPTCHA_FAIL.getValue());

            throw new ServerException("验证码错误");
        }

        Authentication authentication;
        try {
            // 用户认证
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ServerException("用户名或密码错误");
        }

        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();
        // 登录时间和token刷新时间
        long date = System.currentTimeMillis();
        user.setLoginTime(date);
        user.setRefreshTokenExpire(DateUtil.offsetSecond(new Date(date), refreshTokenExpire).getTime());
        // 生成 accessToken
        String accessToken = TokenUtils.generator();

        // 保存用户信息到缓存
        tokenStoreCache.saveUser(accessToken, user);

        return new SysTokenVO(accessToken);
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
        String accessToken = TokenUtils.generator();

        // 保存用户信息到缓存
        tokenStoreCache.saveUser(accessToken, user);

        return new SysTokenVO(accessToken);
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
}
