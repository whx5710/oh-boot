package com.iris.sys.base.enums;


import java.util.Objects;

/**
 * 用户状态
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public enum UserStatusEnum {
    /**
     * 停用
     */
    DISABLE(0, "停用"),
    /**
     * 正常
     */
    ENABLED(1, "正常");

    private final int value;
    private final String name;

    public static String getNameByValue(int value) {
        for (UserStatusEnum s : UserStatusEnum.values()) {
            if (s.getValue() == value) {
                return s.getName();
            }
        }
        return "";
    }

    public static Integer getValueByName(String name) {
        for (UserStatusEnum s : UserStatusEnum.values()) {
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

    UserStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
