package com.iris.framework.datasource.config.auto;

import com.iris.framework.common.constant.Constant;
import com.iris.framework.datasource.config.DataSourceProperty;
import com.iris.framework.datasource.config.DynamicDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源信息配置类，读取数据源配置信息并注册成bean
 * 主数据源获取顺序 primary > masterDb > sysDb
 * @author 王小费 whx5710@qq.com
 */
@Configuration
public class DynamicDataSourceConfig {
    private final Logger log = LoggerFactory.getLogger(DynamicDataSourceConfig.class);

    private final DynamicDataSourceProperties dynamicDataSourceProperties;

    public DynamicDataSourceConfig(DynamicDataSourceProperties dynamicDataSourceProperties){
        this.dynamicDataSourceProperties = dynamicDataSourceProperties;
    }

    @Bean
    public DynamicDataSource dynamicDataSource(){
        Map<String, DataSourceProperty> map = dynamicDataSourceProperties.getDynamic();
        Map<Object, Object> dataSourceMap = new HashMap<>(map.size());
        log.debug("初始化动态数据源，数据源{}个", map.size());
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
        // 设置默认数据源
        String primary = dynamicDataSourceProperties.getSysDataSource().getPrimary();
        String master = null;
        if(primary == null || primary.equals("")){
            if(masterDataSource == null){
                if(dataSourceMap.containsKey(Constant.SYS_DB)){
                    master = Constant.SYS_DB;
                    dynamicDataSource.setDefaultTargetDataSource(dataSourceMap.get(Constant.SYS_DB));
                }else{
                    log.error("请配置连接主库！primary > masterDb > sysDb");
                }
            }else {
                master = Constant.MASTER_DB;
                dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
            }
        }else{
            master = primary;
            if(dataSourceMap.containsKey(primary)){
                dynamicDataSource.setDefaultTargetDataSource(dataSourceMap.get(primary));
            }else{
                log.error("未找到对应的主数据源[{}]，请检查", master);
            }
        }
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        // 将数据源信息备份在 defineTargetDataSources 中
        dynamicDataSource.setDefineTargetDataSources(dataSourceMap);
        log.debug("动态数据源初始完成，主数据源 [{}]", master);
        return dynamicDataSource;
    }
}
