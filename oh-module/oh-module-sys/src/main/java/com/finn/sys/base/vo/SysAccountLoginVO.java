package com.finn.sys.base.vo;

import com.finn.framework.utils.annotations.RequestKeyParam;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * 账号登录
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "账号登录")
public class SysAccountLoginVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @RequestKeyParam // 结合 @Idempotent 判断是否重复请求
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "唯一key")
    private String key;

    @Schema(description = "验证码")
    private String captcha;

    @Schema(description = "用户密钥，用于第三方系统登录")
    private String userKey;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
