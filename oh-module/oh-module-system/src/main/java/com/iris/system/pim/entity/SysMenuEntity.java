package com.iris.system.pim.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iris.framework.common.entity.BaseEntity;

import java.util.Objects;

/**
 * 菜单管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@TableName("sys_menu")
public class SysMenuEntity extends BaseEntity {
	/**
	 * 上级ID，一级菜单为0
	 */
	private Long pid;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单URL
	 */
	private String url;
	/**
	 * 授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)
	 */
	private String authority;
	/**
	 * 类型   0：菜单   1：按钮   2：接口
	 */
	private Integer type;
	/**
	 * 打开方式   0：内部   1：外部
	 */
	private Integer openStyle;
	/**
	 * 菜单图标
	 */
	private String icon;
	/**
	 * 排序
	 */
	private Integer sort;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOpenStyle() {
		return openStyle;
	}

	public void setOpenStyle(Integer openStyle) {
		this.openStyle = openStyle;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SysMenuEntity that)) return false;
		return Objects.equals(getPid(), that.getPid()) && Objects.equals(getName(), that.getName()) && Objects.equals(getUrl(), that.getUrl()) && Objects.equals(getAuthority(), that.getAuthority()) && Objects.equals(getType(), that.getType()) && Objects.equals(getOpenStyle(), that.getOpenStyle()) && Objects.equals(getIcon(), that.getIcon()) && Objects.equals(getSort(), that.getSort());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPid(), getName(), getUrl(), getAuthority(), getType(), getOpenStyle(), getIcon(), getSort());
	}
}