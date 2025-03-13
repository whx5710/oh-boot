package com.finn.sys.base.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;

import java.time.LocalDateTime;

/**
 * 登录日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@TableName("sys_log_login")
public class SysLogLoginEntity {
    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录IP
     */
    private String ip;

    /**
     * 登录地点
     */
    private String address;

    /**
     * User Agent
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 登录状态  0：失败   1：成功
     */
    private Integer status;

    /**
     * 操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误
     */
    private Integer operation;
    // 租户ID
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}