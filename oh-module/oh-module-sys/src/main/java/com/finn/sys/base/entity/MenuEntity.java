package com.finn.sys.base.entity;

import com.finn.framework.entity.BaseEntity;

/**
 * 菜单管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class MenuEntity extends BaseEntity {
	/**
	 * 上级ID，一级菜单为0
	 */
	private Long parentId;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 上级名称
	 */
	private String parentName;
	/**
	 * 菜单URL
	 */
	private String url;

	// 菜单路径
	private String menuPath;
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
	 * 是否缓存
	 */
	private Boolean noCache;

	/**
	 * 备注
	 */
	private String mark;
	/**
	 * 排序
	 */
	private Integer sort;

	// 用于配置页面是否固定标签页，固定后页面不可关闭。
	Boolean affixTab = false;
	// 用于配置当前路由不使用基础布局，仅在顶级时生效。默认情况下，所有的路由都会被包裹在基础布局中（包含顶部以及侧边等导航部件）
	Boolean noBasicLayout = false;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
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

	public Boolean getNoCache() {
		return noCache;
	}

	public void setNoCache(Boolean noCache) {
		this.noCache = noCache==null?true:noCache;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Boolean getAffixTab() {
		return affixTab;
	}

	public void setAffixTab(Boolean affixTab) {
		this.affixTab = affixTab;
	}

	public Boolean getNoBasicLayout() {
		return noBasicLayout;
	}

	public void setNoBasicLayout(Boolean noBasicLayout) {
		this.noBasicLayout = noBasicLayout;
	}
}