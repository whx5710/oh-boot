package com.finn.framework.security.mobile;

/**
 * 手机短信登录，验证码效验
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface MobileVerifyCodeService {

    boolean verifyCode(String mobile, String code);
}
