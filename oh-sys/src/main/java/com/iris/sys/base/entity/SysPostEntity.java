package com.iris.sys.base.entity;

import com.iris.framework.common.entity.BaseEntity;

import java.util.Objects;

/**
 * 岗位管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class SysPostEntity extends BaseEntity {
	/**
	* 岗位编码
	*/
	private String postCode;
	/**
	* 岗位名称
	*/
	private String postName;
	/**
	* 排序
	*/
	private Integer sort;
	/**
	* 状态  0：停用   1：正常
	*/
	private Integer status;

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SysPostEntity that)) return false;
		return Objects.equals(getPostCode(), that.getPostCode()) && Objects.equals(getPostName(), that.getPostName()) && Objects.equals(getSort(), that.getSort()) && Objects.equals(getStatus(), that.getStatus());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPostCode(), getPostName(), getSort(), getStatus());
	}
}