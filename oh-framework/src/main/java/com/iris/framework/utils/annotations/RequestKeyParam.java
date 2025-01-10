package com.iris.framework.utils.annotations;

import java.lang.annotation.*;

/**
 * 参数注解；加上这个注解可以将参数设置为key，用于判断是否重复提交
 * @author 王小费 whx5710@qq.com
 * 2025-01-10
 */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestKeyParam {

}
