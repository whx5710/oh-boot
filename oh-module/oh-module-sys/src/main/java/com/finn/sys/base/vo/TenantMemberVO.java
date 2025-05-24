package com.finn.sys.base.vo;

import com.finn.core.entity.IDEntity;

/**
 * 租户信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 2025-01-18
 */
public class TenantMemberVO extends IDEntity {
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 备注
     */
    private String note;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态  0：停用   1：正常
     */
    private Integer status;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

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
}
