package com.finn.support.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.entity.BaseEntity;


/**
 * 用户角色关系
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@TableName("sys_user_role")
public class UserRoleEntity extends BaseEntity {

	/**
	 * 角色ID
	 */
	@TableField("role_id")
	private Long roleId;
	/**
	 * 用户ID
	 */
	@TableField("user_id")
	private Long userId;

	@TableField(exists = false)
	private String tenantId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
