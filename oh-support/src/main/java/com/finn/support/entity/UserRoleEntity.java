package com.finn.support.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.entity.BaseEntity;


/**
 * 用户角色关系
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class UserRoleEntity extends BaseEntity {

	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 用户ID
	 */
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

	@Override
	public String getTenantId() {
		return tenantId;
	}

	@Override
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
