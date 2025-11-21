package com.finn.sys.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.finn.core.utils.DateUtils;
import com.finn.core.utils.TreeNode;
import com.finn.framework.datasource.annotations.TableField;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 菜单管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class MenuTreeVO extends TreeNode<MenuTreeVO> {

    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 上级ID，一级菜单为0
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 显示路径
     */
    private String path;

    /**
     * 页面组件路径
     */
    private String menuPath;

    /**
     * 状态 0停用 1有效
     */
    private Integer status;

    /**
     * 菜单是否隐藏
     */
    private Boolean hideInMenu;

    /**
     * 标签是否隐藏
     */
    private Boolean hideInTab;

    /**
     * 用于配置页面的徽标，会在菜单显示
     */
    private String badge;

    /**
     * 用于配置页面的徽标类型，dot 为小红点，normal 为文本
     */
    private String badgeType;

    /**
     * 用于配置页面的徽标颜色
     */
    private String badgeVariants;

    /**
     * 用于配置页面是否固定标签页，固定后页面不可关闭
     */
    private Boolean affixTab;

    /**
     * 用于配置外链跳转路径
     */
    private String link;

    /**
     * 类型   1：菜单   2：按钮   3：接口
     */
    private Integer type = 1;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单缓存
     */
    private Boolean keepAlive = false;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String mark;


    /**
     * 打开方式   0：内部   1：外部
     */
    @Min(value = 0, message = "打开方式不正确")
    @Max(value = 1, message = "打开方式不正确")
    private Integer openStyle;


    /**
     * 授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)
     */
    private String authority;
    /**
     * 上级菜单名称
     */
    private String parentName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @TableField("create_time")
    private LocalDateTime createTime;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getOpenStyle() {
        return openStyle;
    }

    public void setOpenStyle(Integer openStyle) {
        this.openStyle = openStyle;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}