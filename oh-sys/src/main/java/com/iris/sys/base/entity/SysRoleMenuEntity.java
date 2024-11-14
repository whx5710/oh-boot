package com.iris.sys.base.entity;

import com.iris.framework.entity.BaseEntity;

import java.util.Objects;

/**
 * 角色菜单关系
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class SysRoleMenuEntity extends BaseEntity {
	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 菜单ID
	 */
	private Long menuId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SysRoleMenuEntity that)) return false;
		return Objects.equals(getRoleId(), that.getRoleId()) && Objects.equals(getMenuId(), that.getMenuId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getRoleId(), getMenuId());
	}
}
