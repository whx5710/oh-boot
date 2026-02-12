package com.finn.system.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.entity.TenantEntity;

/**
 * 角色数据权限
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@TableName("sys_role_data_scope")
public class RoleDataScopeEntity extends TenantEntity {
	/**
	 * 角色ID
	 */
	@TableField("role_id")
	private Long roleId;
	/**
	 * 部门ID
	 */
	@TableField("dept_id")
	private Long deptId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
}