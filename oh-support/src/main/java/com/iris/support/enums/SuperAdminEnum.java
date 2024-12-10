package com.iris.support.enums;

import java.util.Objects;

/**
 * 超级管理员枚举
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public enum SuperAdminEnum {
    /**
     * 是
     */
    YES(1, "是"),
    /**
     * 否
     */
    NO(0, "否");

    private final Integer value;
    private final String name;

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    SuperAdminEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameByValue(int value) {
        for (SuperAdminEnum s : SuperAdminEnum.values()) {
            if (s.getValue() == value) {
                return s.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name) {
        for (SuperAdminEnum s : SuperAdminEnum.values()) {
            if (Objects.equals(s.getName(), name)) {
                return s.getValue();
            }
        }
        return null;
    }
}