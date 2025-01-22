package com.iris.sys.base.entity;

import com.iris.framework.datasource.annotations.TableField;
import com.iris.framework.datasource.annotations.TableName;
import com.iris.framework.entity.BaseEntity;

/**
 * 租户信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2025-01-18
 */
@TableName("tenant_member")
public class TenantMemberEntity extends BaseEntity {

    @TableField("tenant_name")
    private String tenantName;

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
}
