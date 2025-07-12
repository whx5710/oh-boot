package com.finn.support.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.finn.core.entity.IDEntity;
import com.finn.core.utils.DateUtils;
import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 参数管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@TableName("sys_params")
public class ParamsEntity extends IDEntity {

    /**
     * 参数名称
     */
    @TableField("param_name")
    private String paramName;

    /**
     * 系统参数   0：否   1：是
     */
    @TableField("param_type")
    private Integer paramType;

    /**
     * 参数键
     */
    @TableField("param_key")
    private String paramKey;

    /**
     * 参数值
     */
    @TableField("param_value")
    private String paramValue;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long  creator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long  updater;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 数据状态标记，0删除1有效
     */
    @TableField("db_status")
    private Integer dbStatus = 1;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getUpdater() {
        return updater;
    }

    public void setUpdater(Long updater) {
        this.updater = updater;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(Integer dbStatus) {
        this.dbStatus = dbStatus;
    }
}