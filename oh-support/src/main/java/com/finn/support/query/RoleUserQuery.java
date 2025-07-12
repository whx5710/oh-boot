package com.finn.support.query;

/**
 * 分配角色查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class RoleUserQuery extends UserQuery {
    /**
     * 角色ID
     */
    private Long roleId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
