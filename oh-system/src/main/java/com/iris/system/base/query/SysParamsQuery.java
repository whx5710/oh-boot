package com.iris.system.base.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.iris.framework.common.query.Query;

import java.util.Objects;

/**
 * 参数管理查询
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Schema(description = "参数管理查询")
public class SysParamsQuery extends Query {
    @Schema(description = "系统参数")
    private Integer paramType;

    @Schema(description = "参数键")
    private String paramKey;

    @Schema(description = "参数值")
    private String paramValue;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysParamsQuery that)) return false;
        return Objects.equals(getParamType(), that.getParamType()) && Objects.equals(getParamKey(), that.getParamKey()) && Objects.equals(getParamValue(), that.getParamValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParamType(), getParamKey(), getParamValue());
    }
}