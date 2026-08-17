package com.finn.urban.query;

import com.finn.framework.query.Query;

import java.time.LocalDateTime;

/**
 * 轨迹坐标表查询
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-17
 */
public class TrajectoryQuery extends Query {

    /**
     * 关键字（备注）
     */
    private String keyWord;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
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
}
