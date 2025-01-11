package com.iris.framework.utils;

import org.springframework.context.ApplicationContext;

/**
 * 获取bean工具类
 * @author 王小费 whx5710@qq.com
 */
public class SpringContextUtil {

    private static ApplicationContext ac;

    public static <T>  T getBean(String beanName, Class<T> clazz) {
        return ac.getBean(beanName, clazz);
    }

    public static void setApplicationContext(ApplicationContext applicationContext){
        ac = applicationContext;
    }
}
