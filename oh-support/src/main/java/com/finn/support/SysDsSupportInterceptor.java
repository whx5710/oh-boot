package com.finn.support;

import com.finn.framework.common.properties.SysDataSourceProperties;
import com.finn.framework.datasource.utils.DynamicDataSourceHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * 根据包名指定相应数据源,系统（人员、角色等）数据源匹配
 * 如果系统数据与业务数据分别在不同的数据库内，则需要配置，否则不需要此配置
 * 此方法的优点是不需要在每个类上注解 @Ds
 * 如果调用的方法有Ds注解，调用其他的有Ds注解，会导致后面调用失效，因此使用包名拦截
 * @author 王小费 whx5710@qq.com
 */
@Component
@Aspect
public class SysDsSupportInterceptor {
    private final Logger log = LoggerFactory.getLogger(SysDsSupportInterceptor.class);

    private final SysDataSourceProperties sysDataSourceProperties;
    public SysDsSupportInterceptor(SysDataSourceProperties sysDataSourceProperties){
        this.sysDataSourceProperties = sysDataSourceProperties;
    }

    /**
     * 匹配系统管理相关包名，使用环绕，切换后需切换回来
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.finn.support.mapper.*Mapper.*(..))")
    public Object dynamicSetDataSource(ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();
        if(Proxy.isProxyClass(target.getClass())) {
            Type[] types = AopUtils.getTargetClass(target).getGenericInterfaces();
            String className = types[0].getTypeName();
            if(className.contains(".mapper.")){
                // 根据yml中的配置使用数据源
                log.debug("{} 使用[{}]数据源", className, sysDataSourceProperties.getSysDefault());
                DynamicDataSourceHolder.setDynamicDataSourceKey(sysDataSourceProperties.getSysDefault());
                try {
                    return joinPoint.proceed();
                } finally {
                    DynamicDataSourceHolder.removeDynamicDataSourceKey();
                    log.debug("清除数据源[{}]", sysDataSourceProperties.getSysDefault());
                }
            }else{
                log.error("数据源切面拦截解析包路径错误，解析得到类名：{}", className);
                return joinPoint.proceed();
            }
        }else{
            return joinPoint.proceed();
        }
    }

}
