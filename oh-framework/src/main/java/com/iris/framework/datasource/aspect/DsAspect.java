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

    //环绕通知-类和方法
    @Around("@within(ds),@annotation(ds)")
    public Object around(ProceedingJoinPoint joinPoint, Ds ds) throws Throwable{
        String key = ds.value();
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
