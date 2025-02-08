package com.finn.core.constant;

/**
 * 公共枚举
 */
public enum CommonEnum {

    PASSWORD_ONE(1, "必须包含小写字母，长度"+Constant.PASSWORD_MIN_LENGTH + "-" + Constant.PASSWORD_MAX_LENGTH+"位"),
    PASSWORD_TWO(2, "必须包含小写字母、数字，长度"+Constant.PASSWORD_MIN_LENGTH + "-" + Constant.PASSWORD_MAX_LENGTH+"位"),
    PASSWORD_THREE(3, "必须包含小写字母、数字、大写字母、特殊字符，长度"+Constant.PASSWORD_MIN_LENGTH + "-" + Constant.PASSWORD_MAX_LENGTH+"位");

    // 成员变量
    private final int code;
    private final String msg;
    // 构造方法
    private CommonEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 普通方法
    public static String getName(int code) {
        for (CommonEnum c : CommonEnum.values()) {
            if (c.getCode() == code) {
                return c.msg;
            }
        }
        return null;
    }

    public int getCode(){
        return this.code;
    }

    public String getMsg(){
        return msg;
    }
}
