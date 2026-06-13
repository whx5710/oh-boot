package com.finn.system.query;

import com.finn.framework.query.Query;

/**
 * 操作日志查询
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class OperateLogQuery extends Query {
    /**
     * 用户
     */
    private String realName;

    /**
     * 模块名
     */
    private String module;

    /**
     * 请求URI
     */
    private String reqUri;

    /**
     * 操作状态
     */
    private Integer status;

    /**
     * 关键字搜索
     */
    private String keyWord;
    /**
     * 开始时间
     */
    String startTime;
    /**
     * 结束时间
     */
    String endTime;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getReqUri() {
        return reqUri;
    }

    public void setReqUri(String reqUri) {
        this.reqUri = reqUri;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
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