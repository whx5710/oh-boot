package com.finn.sys.base.enums;


/**
 * 数据范围枚举
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public enum DataScopeEnum {
    /**
     * 全部数据
     */
    ALL(0),
    /**
     * 本部门及子部门数据
     */
    ORG_AND_CHILD(1),
    /**
     * 本部门数据
     */
    ORG_ONLY(2),
    /**
     * 本人数据
     */
    SELF(3),
    /**
     * 自定义数据
     */
    CUSTOM(4);

    private final Integer value;

    public Integer getValue() {
        return value;
    }

    DataScopeEnum(Integer value) {
        this.value = value;
    }
}