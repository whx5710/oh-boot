package com.finn.system.service;

import com.finn.system.vo.AccountLoginVO;
import com.finn.system.vo.MobileLoginVO;
import com.finn.system.vo.TokenVO;
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
     * 用户点击微信登录
     *     ↓
     * 小程序调用 wx.login() 获取 code
     *     ↓
     * 前端将 code 发送到后端
     *     ↓
     * 后端用 code + appid + secret 请求微信接口获取 openid/session_key
     *     ↓
     * 后端根据 openid 查找或创建用户
     *     ↓
     * 后端生成 JWT Token 返回给前端
     *     ↓
     * 前端存储 token，后续请求携带 Authorization: Bearer <token>
     * @param code
     * @return
     */
    TokenVO wechatLogin(String code);

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
