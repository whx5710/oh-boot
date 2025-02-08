package com.finn.framework.datasource.annotations;

import java.lang.annotation.*;

/**
 * 表名注解
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TableName {
    String value();
}
