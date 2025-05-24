package com.finn.support.query;

import com.finn.framework.query.Query;

/**
 * 登录日志查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class LogLoginQuery extends Query {
    /**
     * 用户名
     */
    private String username;

    /**
     * 登录地点
     */
    private String address;

    /**
     * 登录状态  0：失败   1：成功
     */
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