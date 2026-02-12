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
}
