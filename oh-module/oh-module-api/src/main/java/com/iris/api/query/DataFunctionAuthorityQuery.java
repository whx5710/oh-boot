package com.iris.api.query;

import com.iris.framework.common.query.Query;


/**
* 客户端接口授权查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-29
*/
public class DataFunctionAuthorityQuery extends Query {
    String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}