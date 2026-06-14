package com.finn.system.vo;

import cn.idev.excel.annotation.ExcelProperty;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 系统错误日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-04-29 18:38:22
 *
 */
public class ErrorLogVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 错误编码
     */
    @ExcelProperty(value = "错误编码")
    private String errCode;

    /**
     * 错误提示
     */
    @ExcelProperty(value = "错误提示")
    private String msg;

    /**
     * 堆栈信息
     */
    @ExcelProperty(value = "堆栈信息")
    private String stackInfo;

    /**
     * 错误发生时间
     */
    @ExcelProperty(value = "错误发生时间")
    private LocalDateTime errTime;

    /**
     * 链路跟踪ID
     */
    @ExcelProperty(value = "链路跟踪ID")
    private String traceId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String note;

    /**
     * 队列拥挤程度0-5
     */
    private BigDecimal score;
    /**
     * 队列大小
     */
    private Integer queueSize;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }
}
