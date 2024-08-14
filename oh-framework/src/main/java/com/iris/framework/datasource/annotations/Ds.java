package com.iris.framework.datasource.annotations;

import com.iris.framework.common.constant.Constant;

import java.lang.annotation.*;

/**
 * 切换数据源注解
 * @author 王小费 whx5710@qq.com
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ds {
    String value() default Constant.MASTER_DB;
}
