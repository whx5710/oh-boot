package com.iris.system.base.entity;

import com.iris.framework.common.entity.BaseEntity;

import java.util.Objects;

/**
 * 参数管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class SysParamsEntity extends BaseEntity {

    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 系统参数
     */
    private Integer paramType;

    /**
     * 参数键
     */
    private String paramKey;

    /**
     * 参数值
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysParamsEntity that)) return false;
        return Objects.equals(getParamName(), that.getParamName()) && Objects.equals(getParamType(), that.getParamType()) && Objects.equals(getParamKey(), that.getParamKey()) && Objects.equals(getParamValue(), that.getParamValue()) && Objects.equals(getRemark(), that.getRemark());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParamName(), getParamType(), getParamKey(), getParamValue(), getRemark());
    }
}