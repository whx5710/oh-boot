package com.finn.system.entity;

import com.finn.framework.aop.annotations.TableField;
import com.finn.framework.aop.annotations.TableName;
import com.finn.common.entity.IDEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 系统错误日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-04-29 18:38:22
 *
 */
@TableName("sys_error_log")
public class ErrorLogEntity extends IDEntity {
    /**
     * 错误编码
     */
    @TableField("err_code")
    private String errCode;

    /**
     * 错误提示
     */
    private String msg;

    /**
     * 堆栈信息
     */
    @TableField("stack_info")
    private String stackInfo;

    /**
     * 错误发生时间
     */
    @TableField("err_time")
    private LocalDateTime errTime;

    /**
     * 备注
     */
    private String note;

    /**
     * 队列拥挤程度0-10
     */
    private BigDecimal score = BigDecimal.valueOf(0);
    /**
     * 队列大小
     */
    @TableField("queue_size")
    private Integer queueSize = 0;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 链路跟踪ID
     */
    @TableField("trace_id")
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
