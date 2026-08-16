package com.finn.system.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部字典
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class DictVO {
    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典数据列表
     */
    private List<DictDataVO> dataList = new ArrayList<>();

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public List<DictDataVO> getDataList() {
        return dataList;
    }

    public void setDataList(List<DictDataVO> dataList) {
        this.dataList = dataList;
    }
}
