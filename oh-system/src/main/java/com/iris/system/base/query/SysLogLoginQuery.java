package com.iris.system.base.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.iris.framework.common.query.Query;

import java.util.Objects;

/**
 * 登录日志查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "登录日志查询")
public class SysLogLoginQuery extends Query {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "登录地点")
    private String address;

    @Schema(description = "登录状态  0：失败   1：成功")
    private Integer status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysLogLoginQuery that)) return false;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getAddress(), getStatus());
    }
}