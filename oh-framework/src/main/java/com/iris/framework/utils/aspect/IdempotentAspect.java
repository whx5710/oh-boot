package com.iris.framework.utils.aspect;

import com.iris.core.cache.RedisCache;
import com.iris.core.cache.RedisKeys;
import com.iris.core.exception.ServerException;
import com.iris.framework.utils.annotations.Idempotent;
import com.iris.framework.utils.annotations.RequestKeyParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 幂等切面实现
 * @author 王小费 whx5710@qq.com
 * 2025-01-10
 */
@Aspect
@Configuration
@Order(2)
public class IdempotentAspect {
    private final static Logger log = LoggerFactory.getLogger(IdempotentAspect.class);

    private final RedisCache redisCache;

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(com.iris.framework.utils.annotations.Idempotent)")
    public void annotation() {
    }

    public IdempotentAspect(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Around("annotation()")
    public Object interceptor(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        if (idempotent.keyPrefix().isEmpty()) {
            throw new ServerException("前缀不能为空");
        }
        // 获取自定义key
        final String lockKey = getLockKey(joinPoint);
        boolean isLocked = false;
        try {
            // 尝试抢占锁, redis的key + 锁定时间
            isLocked = redisCache.tryLock(lockKey, idempotent.timeout(), idempotent.timeUnit());
            // 没有拿到锁说明已经有了请求了
            if(!isLocked){
                throw new ServerException(idempotent.message());
            }
            try {
                log.debug("锁定请求，防止重复操作");
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                log.error("系统异常", throwable);
                throw new ServerException(throwable.getMessage());
            }
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        } finally {
            // 释放锁
            if (isLocked && !idempotent.limit()) {
                redisCache.unlock(lockKey);
            }
        }
    }

    /**
     * 获取LockKey
     *
     * @param joinPoint 切入点
     * @return key
     */
    private String getLockKey(ProceedingJoinPoint joinPoint) {
        //获取连接点的方法签名对象
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        //Method对象
        Method method = methodSignature.getMethod();
        //获取Method对象上的注解对象
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        //获取方法参数
        final Object[] args = joinPoint.getArgs();
        //获取Method对象上所有的注解
        final Parameter[] parameters = method.getParameters();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            final RequestKeyParam keyParam = parameters[i].getAnnotation(RequestKeyParam.class);
            //如果属性不是RequestKeyParam注解，则不处理
            if (keyParam == null) {
                continue;
            }
            //如果属性是RequestKeyParam注解，则拼接 连接符 ": + RequestKeyParam"
            sb.append(idempotent.delimiter()).append(args[i]);
        }
        //如果方法上没有加RequestKeyParam注解
        if (sb.toString().isEmpty()) {
            //获取方法上的多个注解（为什么是两层数组：因为第二层数组是只有一个元素的数组）
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            //循环注解
            for (int i = 0; i < parameterAnnotations.length; i++) {
                final Object object = args[i];
                //获取注解类中所有的属性字段
                final Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    //判断字段上是否有RequestKeyParam注解
                    final RequestKeyParam annotation = field.getAnnotation(RequestKeyParam.class);
                    //如果没有，跳过
                    if (annotation == null) {
                        continue;
                    }
                    //如果有，设置Accessible为true,启用安全检查（为true时可以使用反射访问私有变量，否则不能访问私有变量）
                    field.setAccessible(true);
                    //如果属性是RequestKeyParam注解，则拼接 连接符": + RequestKeyParam"
                    sb.append(idempotent.delimiter()).append(ReflectionUtils.getField(field, object));
                }
            }
        }
        //返回指定前缀的key
        return RedisKeys.PREFIX + "idempotent:" + idempotent.keyPrefix() + sb;
    }
}
