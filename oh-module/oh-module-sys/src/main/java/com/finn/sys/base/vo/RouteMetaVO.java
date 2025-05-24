package com.finn.sys.base.vo;

import java.util.List;

/**
 * 路由meta属性
 * @author 王小费
 * @since 2025-04-20
 */
public class RouteMetaVO {
    /**
     * 用于配置页面的标题，会在菜单和标签页中显示。一般会配合国际化使用
     */
    String title;
    /**
     * 用于配置页面的图标，会在菜单和标签页中显示。一般会配合图标库使用，如果是http链接，会自动加载图片
     */
    String icon;
    /**
     * 用于配置页面的权限，只有拥有对应权限的用户才能访问页面
     */
    List<String> authority;
    /**
     * 用于配置内嵌页面的 iframe 地址，设置后会在当前页面内嵌对应的页面
     */
    String iframeSrc;
    /**
     * 用于配置外链跳转路径，会在新窗口打开
     */
    String link;
    /**
     * 用于配置页面是否开启缓存，开启后页面会缓存，不会重新加载，仅在标签页启用时有效
     */
    Boolean keepAlive = false;
    /**
     * 用于配置页面的排序，用于路由到菜单排序(sort)
     */
    Integer order;
    /**
     * 设置为 true 时，会在新窗口打开页面
     */
    Boolean openInNewWindow = false;
    /**
     * 用于配置页面是否在菜单中隐藏，隐藏后页面不会在菜单中显示
     */
    Boolean hideInMenu = false;
    /**
     * 标签是否隐藏
     */
    Boolean hideInTab = false;
    /**
     * 用于配置页面是否在面包屑中隐藏，隐藏后页面不会在面包屑中显示
     */
    Boolean hideInBreadcrumb = false;
    /**
     * 用于配置页面是否固定标签页，固定后页面不可关闭。
     */
    Boolean affixTab = false;
    /**
     * 用于配置当前路由不使用基础布局，仅在顶级时生效。默认情况下，所有的路由都会被包裹在基础布局中（包含顶部以及侧边等导航部件）
     */
    Boolean noBasicLayout = false;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<String> getAuthority() {
        return authority;
    }

    public void setAuthority(List<String> authority) {
        this.authority = authority;
    }

    public String getIframeSrc() {
        return iframeSrc;
    }

    public void setIframeSrc(String iframeSrc) {
        this.iframeSrc = iframeSrc;
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getOpenInNewWindow() {
        return openInNewWindow;
    }

    public void setOpenInNewWindow(Boolean openInNewWindow) {
        this.openInNewWindow = openInNewWindow;
    }

    public Boolean getHideInMenu() {
        return hideInMenu;
    }

    public void setHideInMenu(Boolean hideInMenu) {
        this.hideInMenu = hideInMenu;
    }

    public Boolean getHideInBreadcrumb() {
        return hideInBreadcrumb;
    }

    public void setHideInBreadcrumb(Boolean hideInBreadcrumb) {
        this.hideInBreadcrumb = hideInBreadcrumb;
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
}
