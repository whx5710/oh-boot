package com.finn.framework.datasource.aspect;

import com.finn.framework.datasource.annotations.Ds;
import com.finn.framework.datasource.utils.DynamicDataSourceHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;


/**
 * 数据源切换拦截
 * @author 王小费 whx5710@qq.com
 */
@Aspect
@Configuration
@EnableAspectJAutoProxy
public class DsAspect {
    private final static Logger log = LoggerFactory.getLogger(DsAspect.class);

    private final Environment environment;

    public DsAspect(Environment environment){
        this.environment = environment;
    }

    //环绕通知-方法
    @Around("@annotation(ds)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint, Ds ds) throws Throwable{
        return proceed(joinPoint, ds);
    }

    //环绕通知-类
    @Around("@within(ds)")
    public Object aroundType(ProceedingJoinPoint joinPoint, Ds ds) throws Throwable{
        return proceed(joinPoint, ds);
    }


    /**
     * 切换数据源
     * @param joinPoint
     * @param ds
     * @return
     * @throws Throwable
     */
    private Object proceed(ProceedingJoinPoint joinPoint, Ds ds) throws Throwable {
        String key = ds.value().trim();
        // 支持动态参数（可配置）如 ${spring.datasource.sys-data-source.sys-default:sysDb}
        if(key.startsWith("${") && key.endsWith("}")){
            key = environment.resolvePlaceholders(key);
        }
        log.debug("切换数据源[{}]", key);
        DynamicDataSourceHolder.setDynamicDataSourceKey(key);
        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceHolder.removeDynamicDataSourceKey();
            log.debug("清除数据源[{}]", key);
        }
    }
}
