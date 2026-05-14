package com.finn.system.enums;

/**
 * 系统参数 枚举
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public enum ParamsEnum {
    // app端登录验证码标识
    APP("APP_LOGIN_CAPTCHA"),
    // PC端登录验证码
    PC("LOGIN_CAPTCHA"),
    /**
     * 验证码类型
     * 1 png类型
     * 2 gif类型
     * 3 中文类型
     * 4 中文gif类型
     * 5 算术类型
     */
    CAPTCHA_TYPE("CAPTCHA_TYPE"),
    // 验证码长度
    CAPTCHA_LENGTH("CAPTCHA_LENGTH");

    private final String code;

    ParamsEnum(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
