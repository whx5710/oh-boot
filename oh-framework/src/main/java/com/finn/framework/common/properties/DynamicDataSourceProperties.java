package com.finn.framework.common.properties;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
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
     * 每一个数据源属性
     */
    private Map<String, DataSourceProperty> dynamic = new LinkedHashMap<>();

    /**
     * 连接池类型，如果不设置自动查找 Druid > HikariCp
     */
    private Class<? extends DataSource> type = DruidDataSource.class;

    /**
     * 系统数据源基础配置
     * @return sys ds
     */
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

    public Class<? extends DataSource> getType() {
        return type;
    }

    public void setType(Class<? extends DataSource> type) {
        this.type = type;
    }
}
