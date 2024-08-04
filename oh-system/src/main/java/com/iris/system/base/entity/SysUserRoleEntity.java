package com.iris.system.base.entity;

import com.iris.framework.common.entity.BaseEntity;

import java.util.Objects;

/**
 * 用户角色关系
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class SysUserRoleEntity extends BaseEntity {

	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 用户ID
	 */
	private Long userId;

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SysUserRoleEntity that)) return false;
		return Objects.equals(getRoleId(), that.getRoleId()) && Objects.equals(getUserId(), that.getUserId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getRoleId(), getUserId());
	}
}
