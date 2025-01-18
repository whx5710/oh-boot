package com.iris.framework.datasource.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表字段注解；字段是否存在表中、指定列名（默认属性名与表字段名一致，否则需指定字段名称）
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableField {

    /**
     * 字段是否存在
     * @return bool
     */
    boolean exists() default true;

    /**
     * 指定列名，与数据库表中字段一致
     * @return 列名
     */
    String value() default "";
}
