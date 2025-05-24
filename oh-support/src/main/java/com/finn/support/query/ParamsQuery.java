package com.finn.support.query;

import com.finn.framework.query.Query;

import java.util.List;

/**
 * 参数管理查询
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class ParamsQuery extends Query {
    /**
     * 类型 - 系统参数 0：否   1：是
     */
    private Integer paramType;

    /**
     * 参数键
     */
    private String paramKey;

    /**
     * 参数值
     */
    private String paramValue;

    // 关键字搜索
    private String keyWord;

    /**
     * ID集合
     */
    List<Long> idList;

    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}