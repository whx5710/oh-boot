package com.finn.framework.datasource.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.finn.core.exception.ServerException;
import com.finn.framework.common.properties.DataSourceProperty;
import com.finn.framework.common.properties.DynamicDataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源信息配置类，读取数据源配置信息并注册成bean <br/>
 * 同过配置支持Druid、Hikari连接池 2025-02-04 <br/>
 * 主数据源获取顺序 primary > masterDb > sysDb <br/>
 * @author 王小费 whx5710@qq.com
 * @since 2024-08-11
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
        String type = dynamicDataSourceProperties.getType().getName(); // DruidDataSource
        // 主数据源
        DataSource masterDataSource = null;
        // 主数据源key
        String primary = dynamicDataSourceProperties.getSysDataSource().getPrimary();

        // 生成数据库连接
        for(Map.Entry<String,DataSourceProperty> item: map.entrySet()){
            DataSourceProperty dataSourceProperty = item.getValue();
            String key = item.getKey();
            // druid,也可以换成其他连接池
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
            if(key.equals(primary)){
                masterDataSource = ds;
            }
        }
        // 组装数据源
        return buildDs(dataSourceMap, masterDataSource, primary);
    }

    /**
     * 组装Druid连接属性
     * @param name name
     * @param dataSourceProperty ds
     * @return map
     */
    private static DataSource createDruidDS(String name,DataSourceProperty dataSourceProperty) throws Exception {
        Map<String, String> properties = new HashMap<>();
        properties.put(DruidDataSourceFactory.PROP_URL, dataSourceProperty.getUrl()); // 地址
        properties.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, dataSourceProperty.getDriverClassName()); // 驱动名
        properties.put(DruidDataSourceFactory.PROP_USERNAME, dataSourceProperty.getUsername()); // 用户名
        properties.put(DruidDataSourceFactory.PROP_PASSWORD, dataSourceProperty.getPassword()); // 密码
        properties.put(DruidDataSourceFactory.PROP_INITIALSIZE, dataSourceProperty.getInitialSize()); // 初始化连接数
        properties.put(DruidDataSourceFactory.PROP_MINIDLE, dataSourceProperty.getMinIdle()); // 最小空闲连接数
        properties.put(DruidDataSourceFactory.PROP_MAXACTIVE, dataSourceProperty.getMaxActive()); // 最大连接数
        properties.put(DruidDataSourceFactory.PROP_MAXWAIT, dataSourceProperty.getMaxWait()); // 获取连接时的最大等待时间，单位为毫秒
        properties.put(DruidDataSourceFactory.PROP_FILTERS, dataSourceProperty.getFilters());
        // 连接属性，慢SQL
        properties.put(DruidDataSourceFactory.PROP_CONNECTIONPROPERTIES, dataSourceProperty.getConnectionProperties());
        properties.put(DruidDataSourceFactory.PROP_NAME, name); // 名称
        return DruidDataSourceFactory.createDataSource(properties);
    }

    /**
     * 配置Hikari连接属性
     * @param key 连接名
     * @param dataSourceProperty 属性
     * @return 数据库连接
     */
    private static HikariDataSource createHikariDS(String key,DataSourceProperty dataSourceProperty){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(dataSourceProperty.getUsername()); // 用户名
        hikariConfig.setPassword(dataSourceProperty.getPassword()); // 密码
        hikariConfig.setJdbcUrl(dataSourceProperty.getUrl()); // url
        hikariConfig.setDriverClassName(dataSourceProperty.getDriverClassName()); // 驱动
        hikariConfig.setMinimumIdle(Integer.parseInt(dataSourceProperty.getMinIdle())); // 最小连接数
        hikariConfig.setMaximumPoolSize(Integer.parseInt(dataSourceProperty.getMaxActive())); // 连接池最大连接数
        hikariConfig.setConnectionTimeout(Long.parseLong(dataSourceProperty.getMaxWait())); // 获取连接时的最大等待时间，单位为毫秒
        hikariConfig.setMaxLifetime(Long.parseLong(dataSourceProperty.getMaxLifetime())); // Hikari属性,控制池中连接的最长生命周期，值0表示无限生命周期，默认30分钟
        hikariConfig.setPoolName(key); // 连接池名称
        try{
            return new HikariDataSource(hikariConfig);
        }catch (HikariPool.PoolInitializationException e){
            throw new ServerException("创建数据库连接失败!", e.getMessage());
        }
    }

    /**
     * 校验数据连接
     * @param dataSource ds
     */
    private void checkDs(DataSource dataSource, String key) throws SQLException {
        Connection connection = null;
        try{
            log.debug("验证数据源[{}]连接是否正常", key);
            connection = dataSource.getConnection();
            log.debug("数据源 {} 连接正常", key);
        }catch (Exception e){
            log.error("初始化数据源[{}]连接失败！ {}", key, e.getMessage());
            throw new ServerException("初始化数据源[" + key + "]连接失败，请检查！");
        }finally {
            if(connection != null){
                connection.close();
            }
        }
    }

    /**
     * 组装数据源
     * @param dataSourceMap 数据源map
     * @param masterDataSource 主数据源
     * @param primary 主数据源名
     * @return ds
     */
    private DynamicDataSource buildDs(Map<Object, Object> dataSourceMap, DataSource masterDataSource, String primary){
        //设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        String master = null;
        String sysDefaultDb = dynamicDataSourceProperties.getSysDataSource().getSysDefault();
        if(primary == null || primary.isEmpty()){
            if(masterDataSource == null){
                if(dataSourceMap.containsKey(sysDefaultDb)){
                    master = sysDefaultDb;
                    dynamicDataSource.setDefaultTargetDataSource(dataSourceMap.get(sysDefaultDb));
                }else{
                    // 优先系统配置的主数据源，其次是 masterDb，再次是 sysDb
                    log.error("请配置连接主库！primary > masterDb > sysDb");
                }
            }else {
                master = primary;
                dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
            }
        }else{
            master = primary;
            if(dataSourceMap.containsKey(primary)){
                dynamicDataSource.setDefaultTargetDataSource(dataSourceMap.get(primary));
            }else{
                log.error("未找到对应的主数据源 [{}]，请检查", master);
                if(dataSourceMap.containsKey(sysDefaultDb)){
                    dynamicDataSource.setDefaultTargetDataSource(dataSourceMap.get(sysDefaultDb));
                    log.warn("未找到配置的主数据源 [{}]，已切换到 [{}] 数据源", master, sysDefaultDb);
                    master = sysDefaultDb;
                }
            }
        }
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        // 将数据源信息备份在 DataSources 中
        dynamicDataSource.setDynamicDataSources(dataSourceMap);
        if(dataSourceMap.containsKey(master)){
            log.info("动态数据源{}初始完成，主数据源 [{}]",dataSourceMap.keySet(), master);
            if(!dataSourceMap.containsKey(sysDefaultDb)){
                log.warn("无系统数据源 [{}]", sysDefaultDb);
            }
            dynamicDataSource.setPrimaryDb(master);
        }else{
            log.error("动态数据源初始完成，无主数据源，请检查");
        }
        return dynamicDataSource;
    }
}
