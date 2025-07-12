package com.finn.sys.base.vo;

import cn.idev.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.finn.core.utils.DateUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class LogOperateVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 操作人
     */
    @ExcelProperty(value = "用户名")
    private String realName;

    /**
     * 模块名
     */
    @ExcelProperty(value = "模块名")
    private String module;

    /**
     * 操作名
     */
    @ExcelProperty(value = "操作名")
    private String name;

    /**
     * 请求URI
     */
    @ExcelProperty(value = "请求URI")
    private String reqUri;

    /**
     * 请求方法
     */
    @ExcelProperty(value = "请求方法")
    private String reqMethod;

    /**
     * 请求参数
     */
    @ExcelProperty(value = "请求参数")
    private String reqParams;

    /**
     * 操作IP
     */
    @ExcelProperty(value = "操作IP")
    private String ip;

    /**
     * 登录地点
     */
    @ExcelProperty(value = "登录地点")
    private String address;

    /**
     * User Agent
     */
    @ExcelProperty(value = "User Agent")
    private String userAgent;

    /**
     * 操作类型
     */
    @ExcelProperty(value = "操作类型")
    private Integer operateType;

    /**
     * 操作类型
     */
    @ExcelProperty(value = "操作类型")
    private String operateTypeLabel;

    /**
     * 执行时长
     */
    @ExcelProperty(value = "执行时长(毫秒)")
    private Integer duration;

    /**
     * 操作状态
     */
    @ExcelProperty(value = "操作状态")
    private Integer status;

    /**
     * 返回消息
     */
    @ExcelProperty(value = "返回消息")
    private String resultMsg;

    /**
     * 租户名称
     */
    @ExcelProperty(value = "租户名称")
    private String tenantName;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReqUri() {
        return reqUri;
    }

    public void setReqUri(String reqUri) {
        this.reqUri = reqUri;
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }

    public String getReqParams() {
        return reqParams;
    }

    public void setReqParams(String reqParams) {
        this.reqParams = reqParams;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getOperateTypeLabel() {
        return operateTypeLabel;
    }

    public void setOperateTypeLabel(String operateTypeLabel) {
        this.operateTypeLabel = operateTypeLabel;
    }
}