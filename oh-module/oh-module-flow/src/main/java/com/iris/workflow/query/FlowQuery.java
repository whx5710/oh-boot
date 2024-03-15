package com.iris.workflow.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.iris.framework.common.query.Query;


/**
* 自定义流程表查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-12-19
*/
@Schema(description = "自定义流程表查询")
public class FlowQuery extends Query {
    @Schema(description = "流程code")
    private String keyCode;

    @Schema(description = "流程名称")
    private String name;

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String key) {
        this.keyCode = keyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}