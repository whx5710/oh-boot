package com.finn.system.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.entity.TenantEntity;

/**
 * 租户信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2025-01-18
 */
@TableName("tenant_member")
public class TenantMemberEntity extends TenantEntity {

    @TableField("tenant_name")
    private String tenantName;

    /**
     * 根部门ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("dept_id")
    private Long deptId;

    private String note;

    private Integer sort;

    private Integer status;

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
