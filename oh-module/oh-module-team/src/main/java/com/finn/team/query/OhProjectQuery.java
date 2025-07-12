package com.finn.team.query;

import com.finn.framework.query.Query;

/**
* 项目信息表查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
public class OhProjectQuery extends Query {
    /**
     * 查询关键字
     */
    String keyWord;

    /**
     * 状态（1开始2暂停3关闭）
     */
    Integer status;
    /**
     * 开始时间
     */
    String startTime;
    /**
     * 结束时间
     */
    String endTime;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}