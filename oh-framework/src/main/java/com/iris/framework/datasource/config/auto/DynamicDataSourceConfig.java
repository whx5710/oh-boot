package com.iris.framework.datasource.config.auto;

import com.iris.framework.common.constant.Constant;
import com.iris.framework.datasource.config.DataSourceProperty;
import com.iris.framework.datasource.config.DynamicDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源信息配置类，读取数据源配置信息并注册成bean
 */
@Configuration
public class DynamicDataSourceConfig {
    private final DynamicDataSourceProperties dynamicDataSourceProperties;

    public DynamicDataSourceConfig(DynamicDataSourceProperties dynamicDataSourceProperties){
        this.dynamicDataSourceProperties = dynamicDataSourceProperties;
    }

    @Bean
    public DynamicDataSource dynamicDataSource(){
        Map<String, DataSourceProperty> map = dynamicDataSourceProperties.getDynamic();
        Map<Object, Object> dataSourceMap = new HashMap<>(map.size());
        DataSource masterDataSource = null;
        for(Map.Entry<String,DataSourceProperty> item: map.entrySet()){
            DataSourceProperty dataSourceProperty = item.getValue();
            String key = item.getKey();
            // hikari
            HikariDataSource hikari = new HikariDataSource();
            hikari.setUsername(dataSourceProperty.getUsername());
            hikari.setPassword(dataSourceProperty.getPassword());
            hikari.setJdbcUrl(dataSourceProperty.getUrl());
            hikari.setDriverClassName(dataSourceProperty.getDriverClassName());
            dataSourceMap.put(key, hikari);
            if(key.equals(Constant.MASTER_DB)){
                masterDataSource = hikari;
            }
        }
        //设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        if(masterDataSource == null){
            dynamicDataSource.setDefaultTargetDataSource(dataSourceMap.get(dynamicDataSourceProperties.getSysDataSource().getPrimary()));
        }else{
            dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        }
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        //将数据源信息备份在defineTargetDataSources中
        dynamicDataSource.setDefineTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }
}
