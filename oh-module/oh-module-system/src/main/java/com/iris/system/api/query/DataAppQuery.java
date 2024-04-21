package com.iris.system.api.query;

import com.iris.framework.common.query.Query;


/**
* 客户端查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-29
*/
public class DataAppQuery extends Query {
    String keyWord;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}