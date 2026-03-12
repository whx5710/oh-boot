package com.finn.framework.datasource.config;

import com.finn.framework.common.properties.ConnectNumProperty;

/**
 * druid配置
 * @author 王小费 whx5710@qq.com
 * @since 2026-03-12
 */
public class DruidProperty extends ConnectNumProperty {
    /**
     * wall,stat
     */
    private String filters;

    /**
     * druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
     */
    private String connectionProperties;

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(String connectionProperties) {
        this.connectionProperties = connectionProperties;
    }
}
