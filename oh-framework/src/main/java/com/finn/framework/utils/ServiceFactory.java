package com.finn.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 * 服务类 注册服务和获取服务
 * 注册的服务编号唯一;根据上下文获取bean
 * @author 王小费 whx5710@qq.com
 */
public class ServiceFactory {

    // 上下文
    private static ApplicationContext ac;

    private final static Logger log = LoggerFactory.getLogger(ServiceFactory.class);

    /**
     * 根据beanName获取java bean
     * @param beanName 名
     * @param clazz 类
     * @return class
     * @param <T> t
     */
    public static <T>  T getBean(String beanName, Class<T> clazz) {
        try {
            return ac.getBean(beanName, clazz);
        }catch (NoSuchBeanDefinitionException e){
            log.error("根据名称【{}】未找到对应的Bean.{}",beanName, e.getMessage());
            return null;
        }
    }

    /**
     * 设置上下文
     * @param applicationContext a
     */
    public static void setApplicationContext(ApplicationContext applicationContext){
        ac = applicationContext;
    }
}
