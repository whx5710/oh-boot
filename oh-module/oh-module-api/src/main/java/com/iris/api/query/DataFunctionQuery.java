package com.iris.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.iris.framework.common.query.Query;

/**
* 功能列表查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-30
*/
@Schema(description = "功能列表查询")
public class DataFunctionQuery extends Query {
    String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}