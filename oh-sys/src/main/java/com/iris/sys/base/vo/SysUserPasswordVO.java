package com.iris.sys.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户修改密码
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Schema(description = "用户修改密码")
public class SysUserPasswordVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "原密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "原密码不能为空")
    private String password;

    @Schema(description = "新密码，密码长度为 4-20 位", requiredMode = Schema.RequiredMode.REQUIRED)
    @Length(min = 6, max = 30, message = "新密码长度为 6-30 位")
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