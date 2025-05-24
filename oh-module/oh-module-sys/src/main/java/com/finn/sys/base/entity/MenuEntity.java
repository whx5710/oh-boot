package com.finn.sys.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.finn.core.entity.IDEntity;
import com.finn.core.utils.DateUtils;
import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 菜单管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@TableName("sys_menu")
public class MenuEntity extends IDEntity {
	/**
	 * 上级ID，一级菜单为0
	 */
	@TableField("parent_id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	@TableField(exists = false)
	private String parentName;
	/**
	 * 菜单名称
	 */
	@TableField("name")
	private String name;

	/**
	 * 标题
	 */
	@TableField("title")
	private String title;

	/**
	 * 显示路径
	 */
	@TableField("path")
	private String path;

	/**
	 * 页面组件路径
	 */
	@TableField("menu_path")
	private String menuPath;

	/**
	 * 状态 0停用 1有效
	 */
	private Integer status;

	/**
	 * 菜单是否隐藏
	 */
	@TableField("hide_in_menu")
	private Boolean hideInMenu = false;

	/**
	 * 标签是否隐藏
	 */
	@TableField("hide_in_tab")
	private Boolean hideInTab = false;

	/**
	 * 授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)
	 */
	@TableField("authority")
	private String authority;

	/**
	 * 用于配置页面的徽标，会在菜单显示
	 */
	@TableField("badge")
	private String badge;

	/**
	 * 用于配置页面的徽标类型，dot 为小红点，normal 为文本
	 */
	@TableField("badge_type")
	private String badgeType;

	/**
	 * 用于配置页面的徽标颜色
	 */
	@TableField("badge_variants")
	private String badgeVariants;

	/**
	 * 用于配置页面是否固定标签页，固定后页面不可关闭
	 */
	@TableField("affix_tab")
	private Boolean affixTab = false;

	/**
	 * 用于配置外链跳转路径
	 */
	@TableField("link")
	private String link;

	/**
	 * catalog | menu | action
	 */
	@TableField("type")
	private String type = "menu";

	/**
	 * 打开方式   0：内部   1：外部
	 */
	@TableField("open_style")
	private Integer openStyle = 0;

	/**
	 * 菜单图标
	 */
	@TableField("icon")
	private String icon;

	/**
	 * 菜单缓存
	 */
	@TableField("keep_alive")
	private Boolean keepAlive = false;

	/**
	 * 排序
	 */
	@TableField("sort")
	private Integer sort = 0;

	/**
	 * 备注
	 */
	@TableField("mark")
	private String mark;

	/**
	 * 创建者
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long  creator;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	@DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	@TableField("create_time")
	private LocalDateTime createTime;

	/**
	 * 更新者
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long  updater;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	@DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	@TableField("update_time")
	private LocalDateTime updateTime;

	/**
	 * 数据状态标记，0删除1有效
	 */
	@TableField("db_status")
	private Integer dbStatus = 1;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public Boolean getHideInMenu() {
		return hideInMenu;
	}

	public void setHideInMenu(Boolean hideInMenu) {
		this.hideInMenu = hideInMenu;
	}

	public Boolean getHideInTab() {
		return hideInTab;
	}

	public void setHideInTab(Boolean hideInTab) {
		this.hideInTab = hideInTab;
	}

	public String getBadge() {
		return badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
	}

	public String getBadgeType() {
		return badgeType;
	}

	public void setBadgeType(String badgeType) {
		this.badgeType = badgeType;
	}

	public String getBadgeVariants() {
		return badgeVariants;
	}

	public void setBadgeVariants(String badgeVariants) {
		this.badgeVariants = badgeVariants;
	}

	public Boolean getAffixTab() {
		return affixTab;
	}

	public void setAffixTab(Boolean affixTab) {
		this.affixTab = affixTab;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
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

	public Boolean getKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(Boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDbStatus() {
		return dbStatus;
	}

	public void setDbStatus(Integer dbStatus) {
		this.dbStatus = dbStatus;
	}
}