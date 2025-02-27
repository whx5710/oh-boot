package com.finn.framework.datasource.annotations;

import java.lang.annotation.*;


/**
 * 切换数据源注解
 * @author 王小费 whx5710@qq.com
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Ds {
    String value();
}
