package com.finn.sys.base.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;

import java.time.LocalDateTime;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@TableName("sys_log_operate")
public class LogOperateEntity {
    /**
     * id
     */
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 操作人
     */
    @TableField("real_name")
    private String realName;

    /**
     * 模块名
     */
    private String module;

    /**
     * 操作名
     */
    private String name;

    /**
     * 请求URI
     */
    @TableField("req_uri")
    private String reqUri;

    /**
     * 请求方法
     */
    @TableField("req_method")
    private String reqMethod;

    /**
     * 请求参数
     */
    @TableField("req_params")
    private String reqParams;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 登录地点
     */
    private String address;

    /**
     * User Agent
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 操作类型
     */
    @TableField("operate_type")
    private Integer operateType;

    /**
     * 执行时长
     */
    private Integer duration;

    /**
     * 操作状态
     */
    private Integer status;

    /**
     * 返回消息
     */
    @TableField("result_msg")
    private String resultMsg;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 创建时间
     */
    @TableField("create_time")
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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}