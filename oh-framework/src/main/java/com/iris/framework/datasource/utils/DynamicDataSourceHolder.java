package com.iris.framework.datasource.utils;

/**
 * 数据源切换处理
 * DynamicDataSourceHolder类主要是设置当前线程的数据源名称
 * 移除数据源名称，以及获取当前数据源的名称，便于动态切换
 * @author 王小费 whx5710@qq.com
 */
public class DynamicDataSourceHolder {

    /**
     * 保存动态数据源名称
     */
    private static final ThreadLocal<String> DYNAMIC_DATASOURCE_KEY = new ThreadLocal<>();
    /**
     * 设置/切换数据源，决定当前线程使用哪个数据源
     */
    public static void setDynamicDataSourceKey(String key){
        DYNAMIC_DATASOURCE_KEY.set(key);
    }
    /**
     * 获取动态数据源名称，默认使用mater数据源
     */
    public static String getDynamicDataSourceKey(){
        return DYNAMIC_DATASOURCE_KEY.get();
    }
    /**
     * 移除当前数据源
     */
    public static void removeDynamicDataSourceKey(){
        DYNAMIC_DATASOURCE_KEY.remove();
    }
}
