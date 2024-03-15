package com.iris.system.pim.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户Token
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "用户登录")
public class SysTokenVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public SysTokenVO(String access_token) {
        this.access_token = access_token;
    }

    @Schema(description = "access_token")
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
