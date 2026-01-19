package com.finn.system.query;

import com.finn.framework.query.Query;

import java.time.LocalDate;

/**
 * 角色管理-角色查询
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class RoleQuery extends Query {
    /**
     * 角色名称
     */
    private String name;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 租户
     */
    private String tenantName;

    /**
     * 是否系统内置1是 0 否
     */
    private Integer isSystem;

    /**
     * 开始时间
     */
    private LocalDate startTime;

    /**
     * 结束时间
     */
    private LocalDate endTime;

    /**
     * 备注
     */
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
}
