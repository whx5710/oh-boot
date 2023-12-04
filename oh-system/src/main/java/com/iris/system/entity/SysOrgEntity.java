package com.iris.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iris.framework.common.entity.BaseEntity;

import java.util.Objects;

/**
 * 机构管理
 * 
 * @author 王小费 whx5710@qq.com
 *
 */
@TableName("sys_org")
public class SysOrgEntity extends BaseEntity {
	/**
	 * 上级ID
	 */
	private Long pid;
	/**
	 * 机构名称
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 上级名称
	 */
	@TableField(exist = false)
	private String parentName;

	// 备注
	private String note;

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SysOrgEntity that)) return false;
		return Objects.equals(getPid(), that.getPid()) && Objects.equals(getName(), that.getName()) && Objects.equals(getSort(), that.getSort()) && Objects.equals(getParentName(), that.getParentName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPid(), getName(), getSort(), getParentName());
	}
}