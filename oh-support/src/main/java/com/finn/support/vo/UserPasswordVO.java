package com.finn.support.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户修改密码
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class UserPasswordVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 原密码
     */
    @NotBlank(message = "原密码不能为空")
    private String password;

    /**
     * 新密码，密码长度为 6-30 位
     */
    @Size(min = 6, max = 30, message = "新密码长度为 6-30 位")
    private String newPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}