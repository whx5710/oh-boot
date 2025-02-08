package com.finn.support.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.entity.BaseEntity;


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

	@TableField(exists = false)
	private String tenantId;

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
	public String getTenantId() {
		return tenantId;
	}

	@Override
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
