package com.finn.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finn.core.entity.SuperEntity;
import com.finn.core.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 路由meta属性
 * @author 王小费
 * @since 2025-04-20
 */
public class RouteMetaVO extends SuperEntity {
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
    private Boolean showBadge = true;

    /**
     * 文本徽章
     */
    private String showTextBadge;

    /**
     * 是否在菜单中隐藏
     */
    private Boolean isHide = false;

    /**
     * 是否在标签页中隐藏
     */
    private Boolean isHideTab = false;

    /**
     * 外部链接
     */
    private String link;

    /**
     * 是否为iframe
     */
    private Boolean isIframe = false;

    /**
     * 全屏页面
     */
    private Boolean isFullPage = false;

    /**
     * 是否缓存
     */
    private Boolean keepAlive = false;

    /**
     * 是否固定标签页
     */
    private Boolean fixedTab = false;

    /**
     * 类型:  catalog | menu | button
     */
    private String type;
    // 排序
    private Integer sort;

    private String mark;

    /**
     * 用于配置页面的权限，只有拥有对应权限的用户才能访问页面
     */
    List<String> authList;

    /**
     * 创建世界
     */
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

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
        this.isHide = hide;
    }

    public Boolean getIsHideTab() {
        return isHideTab;
    }

    public void setIsHideTab(Boolean hideTab) {
        this.isHideTab = hideTab;
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
        this.isIframe = iframe;
    }

    public Boolean getIsFullPage() {
        return isFullPage; }

    public void setIsFullPage(Boolean isFullPage) {
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

    public List<String> getAuthList() {
        return authList;
    }

    public void setAuthList(List<String> authList) {
        this.authList = authList;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
