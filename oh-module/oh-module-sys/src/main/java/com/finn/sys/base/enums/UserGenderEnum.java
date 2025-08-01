package com.finn.sys.base.enums;

import java.util.Objects;

/**
 * 用户性别状态
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public enum UserGenderEnum {
    /**
     * 男
     */
    MAN(0, "男"),
    /**
     * 女
     */
    WOMEN(1, "女"),
    /**
     * 未知
     */
    UNKNOWN(2,"未知");

    private final int value;
    private final String name;

    public static String getNameByValue(int value) {
        for (UserGenderEnum s : UserGenderEnum.values()) {
            if (s.getValue() == value) {
                return s.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name) {
        for (UserGenderEnum s : UserGenderEnum.values()) {
            if (Objects.equals(s.getName(), name)) {
                return s.getValue();
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    UserGenderEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
