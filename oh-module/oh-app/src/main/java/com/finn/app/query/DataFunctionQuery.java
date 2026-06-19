package com.finn.app.query;

import com.finn.framework.query.Query;

/**
* 功能列表查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-30
*/
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