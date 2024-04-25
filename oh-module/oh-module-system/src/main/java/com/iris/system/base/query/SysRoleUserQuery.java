package com.iris.system.base.query;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * 分配角色查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "分配角色查询")
public class SysRoleUserQuery extends SysUserQuery {
    @Schema(description = "角色ID")
    private Long roleId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysRoleUserQuery that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getRoleId(), that.getRoleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRoleId());
    }
}
