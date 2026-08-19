package com.finn.urban.entity;

import com.finn.framework.aop.annotations.TableField;
import com.finn.framework.aop.annotations.TableName;
import com.finn.framework.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * 运动记录表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-19
 */
@TableName("ur_sport_record")
public class SportRecordEntity extends BaseEntity {

    private String name;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 记录日期，yyyymmdd
     */
    @TableField("record_date")
    private String recordDate;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 时长，单位秒
     */
    private Long duration;

    /**
     * 备注
     */
    private String remark;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
