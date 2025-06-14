package com.finn.framework.datasource.aspect;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.finn.core.exception.ServerException;
import com.finn.framework.query.Query;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.ObjectUtils;

import java.util.Map;

import static com.finn.core.constant.Constant.PAGE_NUM;
import static com.finn.core.constant.Constant.PAGE_SIZE;

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
    @Pointcut("@annotation(com.finn.framework.datasource.annotations.Pages)")
    public void annotation() {
    }

    /**
     * 环绕增强，进行分页查询，并将数据总数赋值给total
     */
    @Around("annotation()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 当前页码
        Integer pageNum = 1;
        Integer mPageNum = 0;
        //每页记录数
        Integer pageSize = 10;
        Integer mPageSize = 0;
        Query query = null;
        //获取被增强方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if(arg instanceof Query) {
                query = (Query) arg;
                pageNum = ObjectUtils.isEmpty(query.getPageNum())? pageNum:query.getPageNum();
                pageSize = ObjectUtils.isEmpty(query.getPageSize())? pageSize:query.getPageSize();
                break;
            }else if(arg instanceof Map<?, ?> map){
                try {
                    if(map.containsKey(PAGE_NUM) && map.get(PAGE_NUM) != null){
                        mPageNum = (Integer) map.get(PAGE_NUM);
                    }
                    if(map.containsKey(PAGE_SIZE) && map.get(PAGE_SIZE) != null){
                        mPageSize = (Integer) map.get(PAGE_SIZE);
                    }
                }catch (Exception e){
                    mPageSize = 0;
                    mPageNum = 0;
                    log.warn("分页参数转换失败！{}", e.getMessage());
                }
            }
        }
        Object result = null;
        try {
            if(query == null){
                if(mPageSize > 0 && mPageNum > 0){
                    pageNum = mPageNum;
                    pageSize = mPageSize;
                }else{
                    throw new ServerException("分页参数异常，请检查！");
                }
            }
            //调用分页插件传入开始页码和页面容量
            try (Page<Object> page = PageHelper.startPage(pageNum, pageSize)) {
                //执行
                result = proceedingJoinPoint.proceed(args);
                if (query != null) {
                    //获取并封装分页后的参数
                    query.setTotal(page.getTotal());
                }/*else {
                    log.warn("参数缺少com.finn.framework.query.Query对象，默认查询第{}页{}条", pageNum, pageSize);
                }*/
            }
        } catch (Exception e) {
            log.error("查询数据库异常",e);
            if(e instanceof ServerException serverException){
                throw new ServerException(serverException.getMessage());
            }else{
                throw new ServerException("查询数据异常，请联系管理员", e);
            }
        }
        return result;
    }
}
