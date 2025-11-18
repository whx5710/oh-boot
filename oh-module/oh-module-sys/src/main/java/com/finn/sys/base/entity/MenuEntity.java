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
     * 上级ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField("parent_id")
    private Long parentId;

    /**
     * 路由路径
     */
    @TableField("path")
    private String path;

    /**
     * 组件名
     */
    @TableField("name")
    private String name;

    @TableField(exists = false)
    private String parentName;

    /**
     * 组件路径
     */
    @TableField("component")
    private String component;

    /**
     * 路由标题
     */
    @TableField("title")
    private String title;

    /**
     * 路由图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 是否显示徽章
     */
    @TableField("show_badge")
    private Boolean showBadge;

    /**
     * 文本徽章
     */
    @TableField("show_text_badge")
    private String showTextBadge;

    /**
     * 是否在菜单中隐藏
     */
    @TableField("is_hide")
    private Boolean isHide;

    /**
     * 是否在标签页中隐藏
     */
    @TableField("is_hide_tab")
    private Boolean isHideTab;

    /**
     * 外部链接
     */
    @TableField("link")
    private String link;

    /**
     * 是否为iframe
     */
    @TableField("is_iframe")
    private Boolean isIframe;

    /**
     * 全屏页面
     */
    @TableField("is_full_page")
    private Boolean isFullPage;
    /**
     * 是否缓存
     */
    @TableField("keep_alive")
    private Boolean keepAlive;

    /**
     * 是否固定标签页
     */
    @TableField("fixed_tab")
    private Boolean fixedTab;

    /**
     * 类型:  catalog | menu | button
     */
    @TableField("type")
    private String type;

    /**
     * 授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)
     */
    @TableField("authority")
    private String authority;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 备注
     */
    @TableField("mark")
    private String mark;

    /**
     * 数据状态标识 0：已删除，1：正常
     */
    @TableField("db_status")
    private Integer dbStatus;

    /**
     * 创建者
     */
    @TableField("creator")
    @JsonSerialize(using=ToStringSerializer.class)
    private Long creator;

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
    @TableField("updater")
    @JsonSerialize(using=ToStringSerializer.class)
    private Long updater;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @TableField("update_time")
    private LocalDateTime updateTime;

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

    public Boolean getIsFullPage(){
        return isFullPage;
    }

    public void setIsFullPage(Boolean isFullPage){
        this.isFullPage = isFullPage;
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
}