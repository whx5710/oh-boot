package com.finn.support.query;

import com.finn.framework.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 机构查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "机构查询")
public class OrgQuery extends Query {
    @Schema(description = "机构名称")
    private String name;

    @Schema(description = "父级ID")
    private Long parentId;

    @Schema(description = "租户ID")
    private String tenantId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
