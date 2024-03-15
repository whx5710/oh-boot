package com.iris.system.pim.enums;

/**
 * 字典数据来源
 *
 * @author 王小费 whx5710@qq.com
 *
 */

public enum DictSourceEnum {
    /**
     * 字典数据
     */
    DICT(0),
    /**
     * 动态SQL
     */
    SQL(1);

    private final int value;

    public int getValue() {
        return value;
    }

    DictSourceEnum(int value) {
        this.value = value;
    }
}
