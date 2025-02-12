package com.finn.framework.datasource.service;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.finn.core.exception.ServerException;
import com.finn.framework.common.properties.DataSourceProperty;
import com.finn.framework.datasource.config.DynamicDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import com.zaxxer.hikari.pool.HikariPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 数据源操作
 * @author 王小费 whx5710@qq.com
 * @since 2025-02-09
 */
public class HandleDataSource {

    private final Logger log = LoggerFactory.getLogger(HandleDataSource.class);
    /**
     * 组装数据源,
     * 主数据源和系统数据源都为空的情况下，默认第一个数据源
     * @param dataSourceMap 数据源集合
     * @param masterDataSource 主数据源
     * @param sysDataSource 系统内置数据源
     * @return 动态数据源
     */
    public DynamicDataSource buildDs(Map<Object, Object> dataSourceMap, DataSource masterDataSource, DataSource sysDataSource){
        //设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(dataSourceMap);

        Map<String, DataSource> dsTmp = new HashMap<>(dataSourceMap.size());
        for(Map.Entry<Object,Object> item: dataSourceMap.entrySet()){
            String key = (String) item.getKey();
            DataSource dataSource = (DataSource) item.getValue();
            // 主数据源
            if(masterDataSource == null){
                if(sysDataSource == null){
                    log.warn("主数据源和系统内置数据源都为空，默认第一个数据源【{}】为主数据源", key);
                    masterDataSource = dataSource;
                }else{
                    log.warn("主数据源为空，默认为系统数据源");
                    masterDataSource = sysDataSource;
                }
            }
            dsTmp.put(key, dataSource);
        }
        // 主数据源
        dynamicDataSource.setPrimaryDb(masterDataSource);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        // 将数据源信息备份在 DynamicDataSource 中
        dynamicDataSource.setDynamicDataSources(dsTmp);
        return dynamicDataSource;
    }

    /**
     * 校验数据连接
     * @param dataSource ds
     */
    public void checkDs(DataSource dataSource, String key) throws SQLException {
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
     * 配置Hikari连接属性
     * @param key 连接名
     * @param dataSourceProperty 属性
     * @return 数据库连接
     */
    public HikariDataSource createHikariDS(String key, DataSourceProperty dataSourceProperty){
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
            HikariDataSource dataSource = new HikariDataSource(hikariConfig);
            // 打印监控日志
            if(dataSourceProperty.getHikariLog()){
                hikariMonitor(key, dataSource);
            }
            return dataSource;
        }catch (HikariPool.PoolInitializationException e){
            throw new ServerException("创建数据库连接失败!", e.getMessage());
        }
    }

    /**
     * 组装Druid连接属性
     * @param name name
     * @param dataSourceProperty ds
     * @return map
     */
    public DataSource createDruidDS(String name,DataSourceProperty dataSourceProperty) throws Exception {
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
     * 连接池配置指标监控
     * @param dataSource 连接对象
     */
    private void hikariMonitor(String name, HikariDataSource dataSource) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            HikariPoolMXBean hikariPoolMXBean = dataSource.getHikariPoolMXBean();
            if (null != hikariPoolMXBean) {
                log.info("{} - 连接总数:{}, 活跃连接数:{}, 空闲连接数:{}, 等待连接数:{}",
                        name,
                        hikariPoolMXBean.getTotalConnections(),
                        hikariPoolMXBean.getActiveConnections(),
                        hikariPoolMXBean.getIdleConnections(),
                        hikariPoolMXBean.getThreadsAwaitingConnection());
            }
        }, 0, 30, TimeUnit.SECONDS);
    }
}
