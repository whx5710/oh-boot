package com.iris.framework.datasource.aspect;

import com.iris.framework.datasource.annotations.Ds;
import com.iris.framework.datasource.utils.DynamicDataSourceHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 数据源切换
 * @author 王小费 whx5710@qq.com
 */
@Aspect
@Component
public class DsAspect {
    private final static Logger log = LoggerFactory.getLogger(DsAspect.class);
    //环绕通知-方法
    @Around("@annotation(ds)")
    public Object around(ProceedingJoinPoint joinPoint, Ds ds) throws Throwable{
        return dynamicDataSource(joinPoint, ds);
    }

    //环绕通知-类
    @Around("@within(ds)")
    public Object aroundClass(ProceedingJoinPoint joinPoint, Ds ds) throws Throwable{
        return dynamicDataSource(joinPoint, ds);
    }

    private Object dynamicDataSource(ProceedingJoinPoint joinPoint, Ds ds) throws Throwable {
        String key = ds.value();
        log.debug("切换数据源[{}]", key);
        DynamicDataSourceHolder.setDynamicDataSourceKey(key);
        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceHolder.removeDynamicDataSourceKey();
        }
    }
}
