package com.finn.app.query;

import com.finn.framework.query.Query;

/**
* 客户端查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-29
*/
public class DataAppQuery extends Query {
    String keyWord;

    /**
     * 客户端ID
     */
    String clientId;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}