package com.iris.framework.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iris.core.utils.DateUtils;
import com.iris.core.entity.IDEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Entity基类
 *
 * @author 王小费 whx5710@qq.com
 */
public abstract class BaseEntity extends IDEntity{
    /**
     * 创建者
     */
    private Long  creator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private Long  updater;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime updateTime;

    /**
     * 数据状态标记，0删除1有效
     */
    private Integer dbStatus = 1;

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

    /**
     * 数据状态标记，0删除1有效
     */
    public Integer getDbStatus() {
        return dbStatus;
    }

    /**
     * 数据状态标记，0删除1有效
     */
    public void setDbStatus(Integer dbStatus) {
        this.dbStatus = dbStatus;
    }
}
