package com.finn.framework.operatelog.annotations;

import com.finn.framework.operatelog.enums.OperateTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    /**
     * 模块名
     */
    String module() default "";

    /**
     * 操作名
     */
    String name();

    /**
     * 操作类型
     */
    OperateTypeEnum[] type() default OperateTypeEnum.OTHER;
}
