package com.finn.sys.base.enums;

/**
 * 菜单类型枚举
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public enum MenuTypeEnum {
    /**
     * 菜单
     */
    MENU("menu"),
    /**
     * 按钮
     */
    BUTTON("button"),
    /**
     * 目录
     */
    CATALOG("catalog");

    private final String value;

    MenuTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
