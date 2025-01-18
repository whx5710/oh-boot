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
     * 幂等的超时时间，默认为 1 秒<br/>
     * 注意：如果执行时间超过它，请求还是会进来
     */
    int timeout() default 1;

    /**
     * 时间单位，默认为 SECONDS 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * redis锁前缀；会在入参的基础上，在最前端拼接上 RedisKeys.PREFIX + idempotent:
     * @return s
     */
    String keyPrefix();

    /**
     * key分隔符
     * @return redis的key
     */
    String delimiter() default ":";

    /**
     * 提示信息，正在执行中的提示
     */
    String message() default "重复请求，请稍后重试";

    /**
     * 为true时，timeout时间内限制请求；即调用完成后，不会清除未过期的锁定，用于限制请求频率，<br/>
     * 比如限制10秒内只能1次请求：limit = true,timeout = 10
     */
    boolean limit() default false;
}
