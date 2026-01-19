package com.finn.system.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.entity.BaseEntity;

/**
 * 参数管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@TableName("sys_params")
public class ParamsEntity extends BaseEntity {

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

}