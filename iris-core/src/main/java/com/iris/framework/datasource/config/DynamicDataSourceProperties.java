package com.iris.framework.datasource.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据库连接配置相关
 * @author 王小费 whx5710@qq.com
 */
@Component
@ConfigurationProperties(prefix = DynamicDataSourceProperties.PREFIX, ignoreInvalidFields=true)
public class DynamicDataSourceProperties {

    public static final String PREFIX = "spring.datasource";
    @NestedConfigurationProperty
    SysDataSourceProperties sysDataSource = new SysDataSourceProperties();
    /**
     * druid每一个数据源属性
     */
    private Map<String, DataSourceProperty> dynamic = new LinkedHashMap<>();


    public SysDataSourceProperties getSysDataSource() {
        return sysDataSource;
    }

    public void setSysDataSource(SysDataSourceProperties sysDataSource) {
        this.sysDataSource = sysDataSource;
    }

    public Map<String, DataSourceProperty> getDynamic() {
        return dynamic;
    }

    public void setDynamic(Map<String, DataSourceProperty> dynamic) {
        this.dynamic = dynamic;
    }
}
