package com.iris.framework.utils.aspect;

import com.iris.core.cache.RedisCache;
import com.iris.core.exception.ServerException;
import com.iris.framework.utils.RequestKeyGenerator;
import com.iris.framework.utils.annotations.Idempotent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

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

    @Autowired
    public IdempotentAspect(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Around("annotation()")
    public Object interceptor(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        if (idempotent.keyPrefix().isEmpty()) {
            throw new ServerException("重复提交前缀不能为空");
        }
        //获取自定义key
        final String lockKey = RequestKeyGenerator.getLockKey(joinPoint);
        // 使用Redisson分布式锁的方式判断是否重复提交
//        RLock lock = redissonClient.getLock(lockKey);
        boolean isLocked = false;
        try {
            //尝试抢占锁
            isLocked = redisCache.tryLock(lockKey, idempotent.timeout(), idempotent.timeUnit());
            //没有拿到锁说明已经有了请求了
            if (!isLocked) {
                throw new ServerException(idempotent.message());
            }
            //拿到锁后设置过期时间
            // lock.lock(idempotent.timeout(), idempotent.timeUnit());
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                log.error("系统异常，", throwable);
                throw new ServerException("系统异常，" + throwable.getMessage());
            }
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        } finally {
            //释放锁
            if (isLocked) {
                redisCache.unlock(lockKey);
            }
        }
    }

}
