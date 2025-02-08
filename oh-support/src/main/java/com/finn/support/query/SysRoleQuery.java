package com.finn.support.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.finn.framework.query.Query;

/**
 * 角色管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Schema(description = "角色查询")
public class SysRoleQuery extends Query {
    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "租户ID")
    private String tenantId;

    @Schema(description = "是否系统内置1是")
    private Integer isSystem;

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
}
