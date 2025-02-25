package com.finn.sys.base.query;

import com.finn.framework.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;

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

}