package com.iris.framework.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 幂等注解,防止重复提交
 * @author 王小费 whx5710@qq.com
 * 2025-01-10
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    /**
     * 幂等的超时时间，默认为 1 秒
     * 注意，如果执行时间超过它，请求还是会进来
     */
    int timeout() default 1;

    /**
     * 时间单位，默认为 SECONDS 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * redis锁前缀
     * @return
     */
    String keyPrefix(); // idempotent

    /**
     * key分隔符
     * @return
     */
    String delimiter() default "|";

    /**
     * 提示信息，正在执行中的提示
     */
    String message() default "重复请求，请稍后重试";
}
