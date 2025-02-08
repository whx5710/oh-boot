package com.finn.sys.quartz.enums;


/**
 * 定时任务并发枚举
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public enum ScheduleConcurrentEnum {
    /**
     * 禁止
     */
    NO(0),
    /**
     * 允许
     */
    YES(1);

    private final int value;

    ScheduleConcurrentEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
