package com.finn.system.query;

import com.finn.framework.query.Query;

import java.time.LocalDateTime;

/**
 * 系统错误日志查询
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-04-29 18:38:22
 *
 */
public class ErrorLogQuery extends Query {
    /**
     * 错误编码
     */
    private String errCode;

    /**
     * 错误发生开始时间
     */
    private LocalDateTime startErrTime;

    /**
     * 错误发生结束时间
     */
    private LocalDateTime endErrTime;

    /**
     * 链路跟踪ID
     */
    private String traceId;

    /**
     * 搜索关键字
     */
    private String keyWord;

    /**
     * 租户ID
     */
    private String tenantId;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public LocalDateTime getStartErrTime() {
        return startErrTime;
    }

    public void setStartErrTime(LocalDateTime startErrTime) {
        this.startErrTime = startErrTime;
    }

    public LocalDateTime getEndErrTime() {
        return endErrTime;
    }

    public void setEndErrTime(LocalDateTime endErrTime) {
        this.endErrTime = endErrTime;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
