package com.iris.framework.datasource.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表字段注解，是否存在、列名
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumn {

    /**
     * 字段是否存在
     * @return bool
     */
    boolean isExists() default true;

    /**
     * 列名
     * @return 列名
     */
    String columnName() default "";
}
