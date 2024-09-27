package com.iris.system.base.service;

import com.iris.system.base.vo.SysAccountLoginVO;
import com.iris.system.base.vo.SysMobileLoginVO;
import com.iris.system.base.vo.SysTokenVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 权限认证服务
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SysAuthService {

    /**
     * 账号密码登录
     *
     * @param login 登录信息
     */
    SysTokenVO loginByAccount(SysAccountLoginVO login);

    /**
     * 第三方用户登录（验证码不校验，密钥必填）
     * @param login 登录信息，用户密钥必填
     * @return token信息
     */
    SysTokenVO loginByUserKey(SysAccountLoginVO login);

    /**
     * 手机短信登录
     *
     * @param login 登录信息
     */
    SysTokenVO loginByMobile(SysMobileLoginVO login);

    SysTokenVO refreshToken(String refreshToken, HttpServletRequest request);

    /**
     * 发送手机验证码
     *
     * @param mobile 手机号
     */
    boolean sendCode(String mobile);

    /**
     * 退出登录
     *
     * @param accessToken accessToken
     */
    void logout(String accessToken);
}
