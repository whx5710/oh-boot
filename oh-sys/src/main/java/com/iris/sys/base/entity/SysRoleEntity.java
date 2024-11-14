package com.iris.sys.base.entity;

import com.iris.framework.entity.BaseEntity;
import com.iris.sys.base.enums.DataScopeEnum;

import java.util.Objects;

/**
 * 角色
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
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
