package com.finn.framework.datasource.config;

import com.finn.framework.common.properties.DataSourceProperty;
import com.finn.framework.common.properties.DynamicDataSourceProperties;
import com.finn.framework.datasource.service.HandleDataSource;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 多数据源信息配置类，读取数据源配置信息并注册成bean <br/>
 * 通过配置支持Druid、Hikari连接池 2025-02-04 <br/>
 * 主数据源获取顺序 primary > masterDb > sysDb <br/>
 * 未匹配到主数据源和系统内置数据源，默认第一个数据源
 * @author 王小费 whx5710@qq.com
 * @since 2024-08-11
 */
@Configuration
public class DynamicDataSourceConfig extends HandleDataSource {
    private final Logger log = LoggerFactory.getLogger(DynamicDataSourceConfig.class);

    private final DynamicDataSourceProperties dynamicDataSourceProperties;

    private final MybatisProperties mybatisProperties;

    public DynamicDataSourceConfig(DynamicDataSourceProperties dynamicDataSourceProperties,
                                   MybatisProperties mybatisProperties){
        this.dynamicDataSourceProperties = dynamicDataSourceProperties;
        this.mybatisProperties = mybatisProperties;
    }

    /**
     * 从配置文件中读取配置，生成数据源
     * @return d
     * @throws Exception e
     */
    @Bean
    public DynamicDataSource dynamicDataSource() throws Exception {
        Map<String, DataSourceProperty> map = dynamicDataSourceProperties.getDynamic();
        Map<Object, Object> dataSourceMap = new LinkedHashMap<>(map.size());
        log.debug("初始化动态数据源，数据源总共 {} 个", map.size());
        String type = dynamicDataSourceProperties.getType().getName(); // DruidDataSource
        // 生成数据库连接
        for(Map.Entry<String,DataSourceProperty> item: map.entrySet()){
            DataSourceProperty dataSourceProperty = item.getValue();
            String key = item.getKey();
            // 连接池
            log.debug("初始化 {} 数据源", key);
            DataSource ds = null;
            if(type.contains("HikariDataSource")){
                ds = createHikariDS(key, dataSourceProperty);
            }else{
                ds = createDruidDS(key, dataSourceProperty);
            }
            // 校验数据库连接是否正常
            if(dataSourceProperty.getCheckConnection()){
                checkDs(ds, key);
            }
            dataSourceMap.put(key, ds);
        }
        // 主数据源key
        String primary = dynamicDataSourceProperties.getSysDataSource().getPrimary();
        // 系统管理内置数据源key
        String sysDb = dynamicDataSourceProperties.getSysDataSource().getSysDefault();
        // 组装数据源
        return buildDs(dataSourceMap, primary, sysDb, mybatisProperties);
    }
}
