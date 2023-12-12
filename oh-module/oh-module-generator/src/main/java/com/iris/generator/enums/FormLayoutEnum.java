package com.iris.generator.enums;

/**
 * 表单布局 枚举
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public enum FormLayoutEnum {
    ONE(1),
    TWO(2);
    
    private final Integer value;

    public Integer getValue() {
        return value;
    }

    FormLayoutEnum(Integer value) {
        this.value = value;
    }
}
