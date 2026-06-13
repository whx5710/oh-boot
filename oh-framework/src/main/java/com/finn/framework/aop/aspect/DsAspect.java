package com.finn.framework.aop.aspect;

import com.finn.framework.aop.annotations.Ds;
import com.finn.framework.datasource.DynamicDataSource;
import com.finn.framework.datasource.DynamicDataSourceHolder;
import com.finn.framework.exception.ServerException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源切换拦截
 * <p>
 * 优化点：
 * 1. 支持嵌套数据源切换（使用栈结构）
 * 2. 缓存占位符解析结果，避免重复解析
 * 3. 切换前校验数据源是否存在
 * 4. 日志性能优化
 *
 * @author 王小费 whx5710@qq.com
 */
@Aspect
@Configuration
@EnableAspectJAutoProxy
public class DsAspect {
    private final static Logger log = LoggerFactory.getLogger(DsAspect.class);

    private final Environment environment;
    private final DynamicDataSource dynamicDataSource;

    /**
     * 占位符解析结果缓存
     */
    private final Map<String, String> resolvedKeyCache = new ConcurrentHashMap<>();

    public DsAspect(Environment environment, DynamicDataSource dynamicDataSource) {
        this.environment = environment;
        this.dynamicDataSource = dynamicDataSource;
    }

    //环绕通知-方法
    @Around("@annotation(ds)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint, Ds ds) throws Throwable {
        return proceed(joinPoint, ds);
    }

    //环绕通知-类
    @Around("@within(ds)")
    public Object aroundType(ProceedingJoinPoint joinPoint, Ds ds) throws Throwable {
        return proceed(joinPoint, ds);
    }


    /**
     * 切换数据源
     *
     * @param joinPoint 连接点
     * @param ds        数据源注解
     * @return 方法执行结果
     * @throws Throwable 异常
     */
    private Object proceed(ProceedingJoinPoint joinPoint, Ds ds) throws Throwable {
        String key = resolveKey(ds.value());

        // 校验数据源是否存在
        if (!dynamicDataSource.containsDataSource(key)) {
            throw new ServerException("数据源[" + key + "]不存在，请检查配置");
        }

        if (log.isDebugEnabled()) {
            log.debug("切换数据源[{}]，当前栈深度: {}", key, DynamicDataSourceHolder.getStackDepth());
        }

        // 使用 push 代替 set，支持嵌套切换
        DynamicDataSourceHolder.pushDynamicDataSourceKey(key);
        try {
            return joinPoint.proceed();
        } finally {
            // 使用 pop 代替 remove，支持嵌套切换
            DynamicDataSourceHolder.popDynamicDataSourceKey();
            if (log.isDebugEnabled()) {
                String currentKey = DynamicDataSourceHolder.getDynamicDataSourceKey();
                log.debug("恢复数据源[{}]，当前数据源: {}", key, currentKey != null ? currentKey : "默认数据源");
            }
        }
    }

    /**
     * 解析数据源key，支持动态参数（可配置）如 ${spring.datasource.sys-data-source.sys-default:sysDb}
     * 优化：缓存解析结果，避免重复解析
     *
     * @param key 原始key
     * @return 解析后的key
     */
    private String resolveKey(String key) {
        if (key == null) {
            return "";
        }
        String trimmedKey = key.trim();
        if (!trimmedKey.startsWith("${")) {
            return trimmedKey;
        }
        // 使用缓存避免重复解析
        return resolvedKeyCache.computeIfAbsent(trimmedKey, k -> {
            String resolved = environment.resolvePlaceholders(k);
            return resolved.trim();
        });
    }
}
