package com.finn.sys.base.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.finn.core.entity.IDEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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
     * 上级ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件名
     */
    private String name;

    private String parentName;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由标题
     */
    private String title;

    /**
     * 路由图标
     */
    private String icon;

    /**
     * 是否显示徽章
     */
    private Boolean showBadge;

    /**
     * 文本徽章
     */
    private String showTextBadge;

    /**
     * 是否在菜单中隐藏
     */
    private Boolean isHide;

    /**
     * 是否在标签页中隐藏
     */
    private Boolean isHideTab;

    /**
     * 外部链接
     */
    private String link;

    /**
     * 是否为iframe
     */
    private Boolean isIframe;

    /**
     * 是否缓存
     */
    private Boolean keepAlive;

    /**
     * 是否固定标签页
     */
    private Boolean fixedTab;

    /**
     * 类型:  catalog | menu | button
     */
    private String type;

    /**
     * 授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)
     */
    private String authority;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String mark;

    private List<String> authList;

    /**
     * 数据状态标识 0：已删除，1：正常
     */
    private Integer dbStatus;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

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

    public Boolean getShowBadge() {
        return showBadge;
    }

    public void setShowBadge(Boolean showBadge) {
        this.showBadge = showBadge;
    }

    public String getShowTextBadge() {
        return showTextBadge;
    }

    public void setShowTextBadge(String showTextBadge) {
        this.showTextBadge = showTextBadge;
    }

    public Boolean getIsHide() {
        return isHide;
    }

    public void setIsHide(Boolean hide) {
        isHide = hide;
    }

    public Boolean getIsHideTab() {
        return isHideTab;
    }

    public void setIsHideTab(Boolean hideTab) {
        isHideTab = hideTab;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getIsIframe() {
        return isIframe;
    }

    public void setIsIframe(Boolean iframe) {
        isIframe = iframe;
    }

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getFixedTab() {
        return fixedTab;
    }

    public void setFixedTab(Boolean fixedTab) {
        this.fixedTab = fixedTab;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
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

    public Integer getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(Integer dbStatus) {
        this.dbStatus = dbStatus;
    }

    public List<String> getAuthList() {
        return authList;
    }

    public void setAuthList(List<String> authList) {
        this.authList = authList;
    }
}
