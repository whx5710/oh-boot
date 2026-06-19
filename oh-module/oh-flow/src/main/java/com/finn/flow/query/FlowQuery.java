package com.finn.flow.query;

import com.finn.framework.query.Query;


/**
* 自定义流程表查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-12-19
*/
public class FlowQuery extends Query {
    /**
     * 流程code
     */
    private String keyCode;

    /**
     * 流程名称
     */
    private String name;
    /**
     *
     */
    private String keyWord;

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}