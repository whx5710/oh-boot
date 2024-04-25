package com.iris.system.base.enums;

/**
 * 登录信息
 *
 * @author 王小费 whx5710@qq.com
 * 
 */

public enum LoginOperationEnum {
    /**
     * 登录成功
     */
    LOGIN_SUCCESS(0),
    /**
     * 退出成功
     */
    LOGOUT_SUCCESS(1),
    /**
     * 验证码错误
     */
    CAPTCHA_FAIL(2),
    /**
     * 账号密码错误
     */
    ACCOUNT_FAIL(3);

    private final int value;

    public int getValue() {
        return value;
    }

    LoginOperationEnum(int value) {
        this.value = value;
    }
}
