package com.iris.system;

import com.iris.framework.common.constant.Constant;
import com.iris.framework.datasource.utils.DynamicDataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * 根据包名指定相应数据源,系统（人员、菜单、角色等）数据源匹配
 * 如果系统数据与业务数据分别在不同的数据库内，则需要配置，否则不需要此配置
 * 此方法的优点是不需要在每个类上注解 @Ds
 * @author 王小费 whx5710@qq.com
 */
@Component
@Aspect
public class SystemDataSourceInterceptor {
    private final Logger log = LoggerFactory.getLogger(SystemDataSourceInterceptor.class);

    // 匹配系统管理相关包名
    @Before("execution(* com.iris.system..mapper.*Mapper.*(..))")
    public void dynamicSetDataSource(JoinPoint joinPoint) throws Exception {
        Object target = joinPoint.getTarget();
        Class<?> clazz = target.getClass();
        if(Proxy.isProxyClass(target.getClass())) {
            Type[] types = AopUtils.getTargetClass(target).getGenericInterfaces();
            String className = types[0].getTypeName();
            if(!className.contains(".mapper.")){
                log.error("数据源切面拦截解析包路径错误，解析得到类名：{}", className);
                return;
            }
            // 根据yml中的配置使用数据源
            log.debug("{} 使用[{}]数据源", className, Constant.SYS_DB);
            DynamicDataSourceHolder.setDynamicDataSourceKey(Constant.SYS_DB);
        }
    }

}
