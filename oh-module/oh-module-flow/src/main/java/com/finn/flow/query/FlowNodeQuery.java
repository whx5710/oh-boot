package com.finn.flow.query;

import com.finn.framework.query.Query;


/**
* 环节定义表查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-01-31
*/
public class FlowNodeQuery extends Query {
    /**
     * 流程定义ID
     * Process_demo20231222:2:35032948206342144
     */
    private String procDefId;

    /**
     * 环节ID
     * StartEvent_begin_a
     */
    private String actDefId;

    private String elementType;

    private String keyWord;

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

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }
}