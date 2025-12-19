package com.finn.support.query;

import com.finn.framework.query.Query;

/**
 * 字典类型查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class DictTypeQuery extends Query {
    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 搜索关键字
     */
    private String keyWord;

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
