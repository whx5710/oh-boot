package com.finn.system.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.entity.BaseEntity;


/**
 * 角色菜单关系
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@TableName("sys_role_menu")
public class RoleMenuEntity extends BaseEntity {
	/**
	 * 角色ID
	 */
	@TableField("role_id")
	private Long roleId;
	/**
	 * 菜单ID
	 */
	@TableField("menu_id")
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

}
