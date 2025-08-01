package com.finn.support.service;

import com.finn.support.vo.AccountLoginVO;
import com.finn.support.vo.MobileLoginVO;
import com.finn.support.vo.TokenVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 权限认证服务
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface AuthService {

    /**
     * 账号密码登录
     *
     * @param login 登录信息
     */
    TokenVO loginByAccount(AccountLoginVO login);

    /**
     * 第三方用户登录（验证码不校验，密钥必填）
     * @param login 登录信息，用户密钥必填
     * @return token信息
     */
    TokenVO loginByUserKey(AccountLoginVO login);

    /**
     * 手机短信登录
     *
     * @param login 登录信息
     */
    TokenVO loginByMobile(MobileLoginVO login);

    /**
     * 刷新token
     * @param refreshToken
     * @param request
     * @return
     */
    TokenVO refreshToken(String refreshToken, HttpServletRequest request);

    /**
     * 退出登录
     *
     * @param accessToken accessToken
     */
    void logout(String accessToken, String refreshToken);
}
