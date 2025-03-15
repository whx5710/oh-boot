package com.finn.support.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.finn.framework.query.Query;

import java.util.List;

/**
 * 参数管理查询
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Schema(description = "参数管理查询")
public class ParamsQuery extends Query {
    @Schema(description = "系统参数")
    private Integer paramType;

    @Schema(description = "参数键")
    private String paramKey;

    @Schema(description = "参数值")
    private String paramValue;

    @Schema(description = "ID集合")
    List<Long> idList;

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

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}