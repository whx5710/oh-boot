package com.iris.framework.datasource.config.auto;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.datasource.config.DataSourceProperty;
import com.iris.framework.datasource.config.DynamicDataSourceProperties;
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

    /**
     * 从配置文件中读取配置，生成数据源
     * @return d
     * @throws Exception e
     */
    @Bean
    public DynamicDataSource dynamicDataSource() throws Exception {
        Map<String, DataSourceProperty> map = dynamicDataSourceProperties.getDynamic();
        Map<Object, Object> dataSourceMap = new HashMap<>(map.size());
        log.debug("初始化动态数据源，数据源总共 {} 个", map.size());
        DataSource masterDataSource = null;
        for(Map.Entry<String,DataSourceProperty> item: map.entrySet()){
            DataSourceProperty dataSourceProperty = item.getValue();
            String key = item.getKey();
            // druid,也可以换成其他连接池
            Map<String, String> properties = getDsMap(key, dataSourceProperty);
            log.debug("初始化 {} 数据源", key);
            DataSource druidDs = DruidDataSourceFactory.createDataSource(properties);
            dataSourceMap.put(key, druidDs);
            if(key.equals(Constant.MASTER_DB)){
                masterDataSource = druidDs;
            }
        }
        //设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 设置默认数据源
        String primary = dynamicDataSourceProperties.getSysDataSource().getPrimary();
        String master = null;
        if(primary == null || primary.isEmpty()){
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
        // 将数据源信息备份在 DataSources 中
        dynamicDataSource.setDynamicDataSources(dataSourceMap);
        log.debug("动态数据源初始完成，主数据源 [{}]", master);
        return dynamicDataSource;
    }

    /**
     * 组装连接属性
     * @param name name
     * @param dataSourceProperty ds
     * @return map
     */
    private static Map<String, String> getDsMap(String name,DataSourceProperty dataSourceProperty) {
        Map<String, String> properties = new HashMap<>();
        properties.put(DruidDataSourceFactory.PROP_URL, dataSourceProperty.getUrl()); // 地址
        properties.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, dataSourceProperty.getDriverClassName()); // 驱动名
        properties.put(DruidDataSourceFactory.PROP_USERNAME, dataSourceProperty.getUsername()); // 用户名
        properties.put(DruidDataSourceFactory.PROP_PASSWORD, dataSourceProperty.getPassword()); // 密码
        properties.put(DruidDataSourceFactory.PROP_INITIALSIZE, dataSourceProperty.getInitialSize()); // 初始化连接数
        properties.put(DruidDataSourceFactory.PROP_MINIDLE, dataSourceProperty.getMinIdle()); // 最小空闲连接数
        properties.put(DruidDataSourceFactory.PROP_MAXACTIVE, dataSourceProperty.getMaxActive()); // 最大连接数
        properties.put(DruidDataSourceFactory.PROP_FILTERS, dataSourceProperty.getFilters());
        // 连接属性，慢SQL
        properties.put(DruidDataSourceFactory.PROP_CONNECTIONPROPERTIES, dataSourceProperty.getConnectionProperties());
        properties.put(DruidDataSourceFactory.PROP_NAME, name); // 名称
        return properties;
    }
}
