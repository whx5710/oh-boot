package com.finn.urban.query;

import com.finn.framework.query.Query;

/**
 * 运动记录表查询
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-19
 */
public class SportRecordQuery extends Query {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 记录日期，yyyymmdd
     */
    private String recordDate;

    /**
     * 记录开始日期，yyyymmdd
     */
    private String startDate;

    /**
     * 记录结束日期，yyyymmdd
     */
    private String endDate;

    /**
     * 关键字（备注）
     */
    private String keyWord;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
