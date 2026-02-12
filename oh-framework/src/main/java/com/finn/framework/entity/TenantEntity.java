package com.finn.framework.entity;

import com.finn.framework.datasource.annotations.TableField;

/**
 * 租户基类
 *
 * @author 王小费 whx5710@qq.com
 */
public abstract class TenantEntity extends BaseEntity {
    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
