package com.iris.system.pim.enums;

/**
 * 短信平台枚举
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public enum SmsPlatformEnum {
    /**
     * 阿里云
     */
    ALIYUN(0),
    /**
     * 腾讯云
     */
    TENCENT(1),
    /**
     * 七牛云
     */
    QINIU(2),
    /**
     * 华为云
     */
    HUAWEI(3);

    private final int value;

    public int getValue() {
        return value;
    }

    SmsPlatformEnum(int value) {
        this.value = value;
    }
}
