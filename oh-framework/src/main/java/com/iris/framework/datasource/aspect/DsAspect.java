package com.iris.framework.datasource.aspect;

import com.iris.framework.datasource.annotations.Ds;
import com.iris.framework.datasource.utils.DynamicDataSourceHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 数据源切换
 * @author 王小费 whx5710@qq.com
 */
@Aspect
@Component
public class DsAspect {

    // 设置Ds注解的切点表达式
    @Pointcut("@annotation(com.iris.framework.datasource.annotations.Ds)")
    public void dynamicDataSourcePointCut(){

    }

    //环绕通知
    @Around("dynamicDataSourcePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        String key = getDefineAnnotation(joinPoint).value();
        DynamicDataSourceHolder.setDynamicDataSourceKey(key);
        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceHolder.removeDynamicDataSourceKey();
        }
    }
    /**
     * 功能描述:先判断方法的注解，后判断类的注解，以方法的注解为准
     * @MethodName: getDefineAnnotation
     * @MethodParam: [joinPoint]
     * @Return: com.wonders.dynamic.DataSource
     * @Author: yyalin
     * @CreateDate: 2023/7/17 14:09
     */
    private Ds getDefineAnnotation(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Ds dataSourceAnnotation = methodSignature.getMethod().getAnnotation(Ds.class);
        if (Objects.nonNull(methodSignature)) {
            return dataSourceAnnotation;
        } else {
            Class<?> dsClass = joinPoint.getTarget().getClass();
            return dsClass.getAnnotation(Ds.class);
        }
    }
}
