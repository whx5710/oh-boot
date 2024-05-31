package com.iris.system.app.query;

import com.iris.framework.common.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;

/**
* 功能列表查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-30
*/
@Schema(description = "功能列表查询")
public class DataFunctionQuery extends Query {
    String clientId;

    String keyWord;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}