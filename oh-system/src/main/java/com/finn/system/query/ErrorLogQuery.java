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
     * 错误提示
     */
    private String msg;

    /**
     * 堆栈信息
     */
    private String stackInfo;

    /**
     * 错误发生时间
     */
    private LocalDateTime errTime;

    /**
     * 链路跟踪ID
     */
    private String traceId;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStackInfo() {
        return stackInfo;
    }

    public void setStackInfo(String stackInfo) {
        this.stackInfo = stackInfo;
    }

    public LocalDateTime getErrTime() {
        return errTime;
    }

    public void setErrTime(LocalDateTime errTime) {
        this.errTime = errTime;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

}
