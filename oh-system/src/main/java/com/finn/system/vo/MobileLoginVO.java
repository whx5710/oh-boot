package com.finn.system.vo;

import java.io.Serial;
import java.io.Serializable;

/**
 * 手机号登录
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class MobileLoginVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 验证码
     */
    private String code;
    /**
     * 由于用户可能是平台用户的同时，还是第三方平台用户（微信、支付宝小程序等其他第三方平台），手机号码相同，
     * 因此，用手机验证码登录时，需确定用户源于哪个平台登录
     */
    private String userType;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
