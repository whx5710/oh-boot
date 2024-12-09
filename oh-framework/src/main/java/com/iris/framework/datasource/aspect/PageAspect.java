package com.iris.framework.datasource.aspect;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.iris.core.exception.ServerException;
import com.iris.framework.query.PageFilter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.ObjectUtils;

/**
 * 分页注解
 * @author 王小费 whx5710@qq.com
 * 2024年12月08日 10:34
 **/
@Configuration
@Aspect //标注当前类是Aop切面类
@EnableAspectJAutoProxy //开启Aop增强
public class PageAspect {

    private final static Logger log = LoggerFactory.getLogger(PageAspect.class);

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(com.iris.framework.datasource.annotations.Page)")
    public void annotation() {
    }

    /**
     * 环绕增强，进行分页查询，并将数据总数赋值给total
     */
    @Around("annotation()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 当前页码
        Integer pageNum = 1;
        //每页记录数
        Integer limit = 10;
        PageFilter<?> pageFilter = null;
        //获取被增强方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if(arg instanceof PageFilter) {
                pageFilter = (PageFilter<?>) arg;
                pageNum = ObjectUtils.isEmpty(pageFilter.getPage())? pageNum:pageFilter.getPage();
                limit = ObjectUtils.isEmpty(pageFilter.getLimit())? limit:pageFilter.getLimit();
            }
        }
        Object result = null;
        try {
            //调用分页插件传入开始页码和页面容量
            Page<Object> page = PageHelper.startPage(pageNum, limit);
            //执行被增强的方法，不写，则被增强方法不执行
            result = proceedingJoinPoint.proceed(args);
            if(pageFilter != null){
                //获取并封装分页后的参数
                pageFilter.setTotal(page.getTotal());
            }
        } catch (Exception e) {
            log.error("查询数据库异常",e);
            throw new ServerException("查询数据库异常");
        }
        return result;
    }
}
