package com.finn.system.query;

import com.finn.framework.query.Query;

/**
 * 岗位管理查询
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class PostQuery extends Query {
    /**
     * 岗位编码
     */
    private String postCode;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 状态  0：停用   1：正常
     */
    private Integer status;
    /**
     * 租户ID
     */
    private String tenantId;

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
