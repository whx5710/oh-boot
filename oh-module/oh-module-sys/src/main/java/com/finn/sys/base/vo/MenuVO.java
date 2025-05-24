package com.finn.sys.base.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finn.core.entity.IDEntity;
import com.finn.core.utils.DateUtils;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class MenuVO extends IDEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String name;
    /**
     * 菜单名称
     */
    private String title;

    /**
     * 上级ID，上一级菜单
     */
    private Long parentId;

    /**
     * 显示路径
     */
    private String path;

    /**
     * 页面组件路径
     */
    private String menuPath;

    /**
     * 类型 catalog | menu | action
     */
    private String type;
    /**
     * 状态 0停用 1有效
     */
    private Integer status;

    /**
     * 菜单是否隐藏
     */
    private Boolean hideInMenu = false;

    /**
     * 标签是否隐藏
     */
    private Boolean hideInTab = false;

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
     * 打开方式   0：内部   1：外部
     */
    @Min(value = 0, message = "打开方式不正确")
    @Max(value = 1, message = "打开方式不正确")
    private Integer openStyle;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)
     */
    private String authority;

    /**
     * 菜单缓存
     */
    private Boolean keepAlive = false;

    /**
     * 排序
     */
    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 上级菜单名称
     */
    private String parentName;

    /**
     * 备注
     */
    private String mark;

    public @NotBlank(message = "菜单名称不能为空") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "菜单名称不能为空") String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
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

    // @Range(min = 0, max = 1, message = "打开方式不正确")
    public void setOpenStyle(@Min(value = 0, message = "打开方式不正确") @Max(value = 1, message = "打开方式不正确") Integer openStyle) {
        this.openStyle = openStyle;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public @Min(value = 0, message = "排序值不能小于0") Integer getSort() {
        return sort;
    }

    public void setSort(@Min(value = 0, message = "排序值不能小于0") Integer sort) {
        this.sort = sort;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
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

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
