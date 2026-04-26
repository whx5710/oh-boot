package com.finn.system;

import com.finn.framework.common.properties.SysDataSourceProperties;
import com.finn.framework.datasource.DynamicDataSourceHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * 根据包名指定相应数据源,系统（人员、菜单、角色等）数据源匹配
 * 如果系统数据与业务数据分别在不同的数据库内，则需要配置，否则不需要此配置
 * 此方法的优点是不需要在每个类上注解 @Ds
 * <p>
 * 注意：此拦截器必须在事务拦截器之前执行，否则数据源切换对事务无效
 * 使用 @Order(-1) 确保在 @Transactional 之前执行
 *
 * @author 王小费 whx5710@qq.com
 */
@Component
@Aspect
@Order(-1)  // 确保在Spring事务拦截器之前执行，事务拦截器默认优先级是最低优先级
public class SysDataSourceInterceptor {
    private final Logger log = LoggerFactory.getLogger(SysDataSourceInterceptor.class);

    private final SysDataSourceProperties sysDataSourceProperties;
    public SysDataSourceInterceptor(SysDataSourceProperties sysDataSourceProperties){
        this.sysDataSourceProperties = sysDataSourceProperties;
    }

    /**
     * 定义切点：拦截系统管理的Service层和Mapper层
     * 拦截Service层是为了在@Transactional开启事务前切换数据源
     * 注意：排除WebSocketHandler，因为它使用了@ServerEndpoint注解，不能被CGLIB代理
     */
    @Pointcut("(execution(* com.finn.system.service.*.*(..)) || execution(* com.finn.system.mapper.*Mapper.*(..))) && !execution(* com.finn.system.service.WebSocketHandler.*(..))")
    public void sysDataSourcePointcut() {}

    /**
     * 匹配系统管理相关包名，使用环绕，切换后需切换回来
     * <p>
     * 重要：此方法必须在事务拦截器之前执行，否则数据源切换对事务无效。
     * Spring事务是在方法调用开始时获取连接，如果此时数据源上下文还未设置，
     * 事务将使用默认数据源。
     *
     * @param joinPoint 连接点
     * @return 方法执行结果
     * @throws Throwable 异常
     */
    @Around("sysDataSourcePointcut()")
    public Object dynamicSetDataSource(ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();
        String className = null;

        // 获取目标类名
        if (Proxy.isProxyClass(target.getClass())) {
            Type[] types = AopUtils.getTargetClass(target).getGenericInterfaces();
            if (types.length > 0) {
                className = types[0].getTypeName();
            }
        } else {
            className = target.getClass().getName();
        }

        // 判断是否属于系统管理包
        if (className != null && (className.contains(".system.service.") || className.contains(".system.mapper."))) {
            // 检查当前线程是否已经设置了该数据源，避免重复设置
            String currentKey = DynamicDataSourceHolder.getDynamicDataSourceKey();
            if (currentKey == null) {
                // 根据yml中的配置使用数据源
                String sysDbKey = sysDataSourceProperties.getSysDefault();
                log.debug("{} 使用[{}]数据源", className, sysDbKey);
                DynamicDataSourceHolder.setDynamicDataSourceKey(sysDbKey);
                try {
                    return joinPoint.proceed();
                } finally {
                    DynamicDataSourceHolder.removeDynamicDataSourceKey();
                    log.debug("{} 清除数据源[{}]", className, sysDbKey);
                }
            } else {
                // 已经设置了数据源，直接执行
                log.debug("{} 复用已设置的数据源[{}]", className, currentKey);
                return joinPoint.proceed();
            }
        } else {
            if (className == null) {
                log.warn("无法解析目标类名，跳过数据源切换");
            }
            return joinPoint.proceed();
        }
    }

}
