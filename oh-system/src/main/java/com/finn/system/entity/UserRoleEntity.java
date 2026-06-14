package com.finn.system.entity;

import com.finn.framework.aop.annotations.TableField;
import com.finn.framework.aop.annotations.TableName;
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

}
