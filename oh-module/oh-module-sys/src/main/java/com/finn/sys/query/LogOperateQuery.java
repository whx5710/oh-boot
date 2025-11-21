package com.finn.sys.query;

import com.finn.framework.query.Query;

/**
 * 操作日志查询
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class LogOperateQuery extends Query {
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
    private String keyWords;
    /**
     * 开始时间
     */
    String startTime;
    /**
     * 结束时间
     */
    String endTime;

    /**
     * 租户
     */
    private String tenantId;

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

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}