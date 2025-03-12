package com.finn.sys.base.vo;

import cn.idev.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.finn.core.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "登录日志")
public class SysLogLoginVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @ExcelProperty(value = "用户名")
    @Schema(description = "用户名")
    private String username;

    @ExcelProperty(value = "登录IP")
    @Schema(description = "登录IP")
    private String ip;

    @ExcelProperty(value = "登录地点")
    @Schema(description = "登录地点")
    private String address;

    @ExcelProperty(value = "User Agent")
    @Schema(description = "User Agent")
    private String userAgent;

    @Schema(description = "登录状态  0：失败   1：成功")
    private Integer status;

    @ExcelProperty(value = "登录状态")
    private String statusLabel;

    @Schema(description = "操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误")
    private Integer operation;

    @ExcelProperty(value = "操作信息")
    private String operationLabel;

    @ExcelProperty(value = "创建时间")
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public String getOperationLabel() {
        return operationLabel;
    }

    public void setOperationLabel(String operationLabel) {
        this.operationLabel = operationLabel;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}