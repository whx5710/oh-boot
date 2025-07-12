package com.finn.sys.base.query;

import com.finn.framework.query.Query;

/**
 * 附件管理查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class AttachmentQuery extends Query {
    /**
     * 附件名称
     */
    private String name;

    /**
     * 租户
     */
    private String tenantId;

    /**
     * 存储平台
     */
    private String platform;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}