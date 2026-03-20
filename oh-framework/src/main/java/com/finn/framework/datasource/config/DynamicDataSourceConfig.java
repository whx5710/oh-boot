package com.finn.framework.datasource.config;

import com.finn.framework.common.properties.DataSourceProperty;
import com.finn.framework.common.properties.DynamicDataSourceProperties;
import com.finn.framework.datasource.service.HandleDataSource;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

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
        // 生成数据库连接
        for(Map.Entry<String,DataSourceProperty> item: map.entrySet()){
            DataSourceProperty dataSourceProperty = item.getValue();
            String key = item.getKey();
            // 连接池
            log.debug("初始化 {} 数据源 {}连接池", key, "Hikari");
            DataSource ds = createHikariDS(key, dataSourceProperty);
            // 校验数据库连接是否正常
            if(dataSourceProperty.getHikari().getCheckConnection()){
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
    
    /**
     * 应用关闭时清理资源
     */
    @EventListener(ContextClosedEvent.class)
    public void onContextClosed() {
        log.info("应用关闭，清理数据源资源...");
        shutdown();
        log.info("数据源资源清理完成");
    }
    
    /**
     * 健康检查组件
     * @return 健康检查服务
     */
    @Bean
    @DependsOn("dynamicDataSource")
    public DataSourceHealthChecker dataSourceHealthChecker() {
        return new DataSourceHealthChecker(this);
    }
    
    /**
     * 数据源健康检查服务
     */
    public static class DataSourceHealthChecker {
        private final HandleDataSource handleDataSource;
        
        public DataSourceHealthChecker(HandleDataSource handleDataSource) {
            this.handleDataSource = handleDataSource;
        }
        
        /**
         * 检查数据源健康状态
         * @param dataSource 数据源
         * @param key 数据源名称
         * @return 是否健康
         */
        public boolean checkHealth(DataSource dataSource, String key) {
            return handleDataSource.healthCheck(dataSource, key);
        }
    }
}
