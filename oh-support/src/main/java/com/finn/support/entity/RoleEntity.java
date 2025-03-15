package com.finn.support.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.entity.BaseEntity;
import com.finn.support.enums.DataScopeEnum;

/**
 * 角色
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
@TableName("sys_role")
public class RoleEntity extends BaseEntity {
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 数据范围  {@link DataScopeEnum}
	 */
	@TableField("data_scope")
	private Integer dataScope;
	/**
	 * 机构ID
	 */
//	@TableField("org_id")
//	private Long orgId;
	/**
	 * 系统内置
	 */
	@TableField("is_system")
	private Integer isSystem;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDataScope() {
		return dataScope;
	}

	public void setDataScope(Integer dataScope) {
		this.dataScope = dataScope;
	}

//	public Long getOrgId() {
//		return orgId;
//	}

//	public void setOrgId(Long orgId) {
//		this.orgId = orgId;
//	}

	public Integer getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
}
