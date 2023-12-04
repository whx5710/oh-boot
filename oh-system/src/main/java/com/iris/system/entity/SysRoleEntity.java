package com.iris.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iris.framework.common.entity.BaseEntity;
import com.iris.system.enums.DataScopeEnum;

import java.util.Objects;

/**
 * 角色
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
@TableName("sys_role")
public class SysRoleEntity extends BaseEntity {
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
	private Integer dataScope;
	/**
	 * 机构ID
	 */
	@TableField(fill = FieldFill.INSERT)
	private Long orgId;

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

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SysRoleEntity that)) return false;
		return Objects.equals(getName(), that.getName()) && Objects.equals(getRemark(), that.getRemark()) && Objects.equals(getDataScope(), that.getDataScope()) && Objects.equals(getOrgId(), that.getOrgId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getRemark(), getDataScope(), getOrgId());
	}
}
