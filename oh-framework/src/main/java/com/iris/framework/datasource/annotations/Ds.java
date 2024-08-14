package com.iris.framework.datasource.annotations;

import com.iris.framework.common.constant.Constant;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ds {
    String value() default Constant.MASTER_DB;
}
