package com.finn.system.entity;

import com.finn.framework.aop.annotations.TableField;
import com.finn.framework.aop.annotations.TableName;
import com.finn.framework.entity.BaseEntity;

/**
 * 角色数据权限
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@TableName("sys_role_data_scope")
public class RoleDataScopeEntity extends BaseEntity {
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