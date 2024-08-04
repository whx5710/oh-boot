package com.iris.system.base.entity;

import com.iris.framework.common.entity.BaseEntity;

import java.util.Objects;

/**
 * 角色数据权限
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class SysRoleDataScopeEntity extends BaseEntity {
	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 机构ID
	 */
	private Long orgId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SysRoleDataScopeEntity that)) return false;
		return Objects.equals(getRoleId(), that.getRoleId()) && Objects.equals(getOrgId(), that.getOrgId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getRoleId(), getOrgId());
	}
}