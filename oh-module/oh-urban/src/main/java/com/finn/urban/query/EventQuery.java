package com.finn.urban.query;

import com.finn.framework.query.Query;

import java.time.LocalDateTime;

/**
 * 事件表查询
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-06-14 17:42:45
 *
 */
public class EventQuery extends Query {

    private String keyWord;
    /**
     * 事件编码
     */
    private String code;

    /**
     * 上报时间 start
     */
    private LocalDateTime reportStartTime;
    /**
     * 上报时间 end
     */
    private LocalDateTime reportEndTime;

    /**
     * 问题描述
     */
    private String description;

    /**
     * 事发位置
     */
    private String location;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 匿名标识1匿名
     */
    private Integer anonymous;

    /**
     * 对应第三方用户ID
     */
    private String openId;

    /**
     * 受理状态，1待处理2处理中3已解决4已驳回
     */
    private String acceptStatus;

    /**
     * 完成时间
     */
    private LocalDateTime completionTime;

    /**
     * 驳回意见
     */
    private String rejectionOpinion;

    /**
     * 处理意见
     */
    private String handlingOpinion;

    /**
     * 备注
     */
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Integer anonymous) {
        this.anonymous = anonymous;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(String acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public LocalDateTime getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(LocalDateTime completionTime) {
        this.completionTime = completionTime;
    }

    public String getRejectionOpinion() {
        return rejectionOpinion;
    }

    public void setRejectionOpinion(String rejectionOpinion) {
        this.rejectionOpinion = rejectionOpinion;
    }

    public String getHandlingOpinion() {
        return handlingOpinion;
    }

    public void setHandlingOpinion(String handlingOpinion) {
        this.handlingOpinion = handlingOpinion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getReportStartTime() {
        return reportStartTime;
    }

    public void setReportStartTime(LocalDateTime reportStartTime) {
        this.reportStartTime = reportStartTime;
    }

    public LocalDateTime getReportEndTime() {
        return reportEndTime;
    }

    public void setReportEndTime(LocalDateTime reportEndTime) {
        this.reportEndTime = reportEndTime;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
