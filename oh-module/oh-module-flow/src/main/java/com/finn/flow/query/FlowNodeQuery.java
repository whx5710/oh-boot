package com.finn.flow.query;

import com.finn.framework.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* 环节定义表查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-01-31
*/
@Schema(description = "环节定义表查询")
public class FlowNodeQuery extends Query {
    /**
     * 流程定义ID
     */
    private String procDefId;

    /**
     * 环节ID
     */
    private String actDefId;

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getActDefId() {
        return actDefId;
    }

    public void setActDefId(String actDefId) {
        this.actDefId = actDefId;
    }
}