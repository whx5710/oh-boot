package com.iris.framework.datasource.utils;

import com.iris.framework.common.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据源切换处理
 * DynamicDataSourceHolder类主要是设置当前线程的数据源名称
 * 移除数据源名称，以及获取当前数据源的名称，便于动态切换
 * @author 王小费 whx5710@qq.com
 */
public class DynamicDataSourceHolder {
    private final static Logger log = LoggerFactory.getLogger(DynamicDataSourceHolder.class);

    /**
     * 保存动态数据源名称
     */
    private static final ThreadLocal<String> DYNAMIC_DATASOURCE_KEY = new ThreadLocal<>();
    /**
     * 设置/切换数据源，决定当前线程使用哪个数据源
     */
    public static void setDynamicDataSourceKey(String key){
        // log.info("数据源切换为：{}",key);
        DYNAMIC_DATASOURCE_KEY.set(key);
    }
    /**
     * 获取动态数据源名称，默认使用mater数据源
     */
    public static String getDynamicDataSourceKey(){
        String key = DYNAMIC_DATASOURCE_KEY.get();
        if(key == null){
            key = Constant.MASTER_DB;
            log.warn("[线程]未获取到数据源，切换到[{}]", key);
        }
        return key;
    }
    /**
     * 移除当前数据源
     */
    public static void removeDynamicDataSourceKey(){
        // log.info("移除数据源：{}",DYNAMIC_DATASOURCE_KEY.get());
        DYNAMIC_DATASOURCE_KEY.remove();
    }
}
