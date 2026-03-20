package com.finn.framework.datasource.service;

import com.finn.core.exception.ServerException;
import com.finn.core.utils.AssertUtils;
import com.finn.framework.common.properties.DataSourceProperty;
import com.finn.framework.datasource.config.DynamicDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import com.zaxxer.hikari.pool.HikariPool;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 数据源操作
 * @author 王小费 whx5710@qq.com
 * @since 2025-02-09
 */
public class HandleDataSource {

    private final Logger log = LoggerFactory.getLogger(HandleDataSource.class);
    
    // 用于管理监控线程池
    private final List<ScheduledExecutorService> monitorExecutors = new ArrayList<>();
    private final AtomicBoolean isShutdown = new AtomicBoolean(false);

    /**
     * 组装数据源,
     * 主数据源和系统数据源都为空的情况下，默认第一个数据源
     * @param dataSourceMap 数据源集合
     * @param primary 主数据源
     * @param sysDb 系统内置数据源
     * @return 动态数据源
     */
    public DynamicDataSource buildDs(Map<Object, Object> dataSourceMap, String primary, String sysDb,
                                     MybatisProperties mybatisProperties){
        //设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        DataSource masterDataSource = null;
        DataSource sysDataSource = null;
        
        // 查找主数据源和系统数据源
        if(dataSourceMap.containsKey(primary)){
            masterDataSource = (DataSource) dataSourceMap.get(primary);
        }
        if(dataSourceMap.containsKey(sysDb)){
            sysDataSource = (DataSource) dataSourceMap.get(sysDb);
        }
        
        // 如果没有找到主数据源或系统数据源，使用第一个数据源作为默认
        if (masterDataSource == null || sysDataSource == null) {
            for (Map.Entry<Object, Object> entry : dataSourceMap.entrySet()) {
                DataSource dataSource = (DataSource) entry.getValue();
                if (masterDataSource == null) {
                    log.warn("未找到主数据源，默认使用{}数据源", entry.getKey());
                    masterDataSource = dataSource;
                    // 如果primary不存在于dataSourceMap中，添加进去
                    if (!dataSourceMap.containsKey(primary)) {
                        dataSourceMap.put(primary, dataSource);
                    }
                }
                if (sysDataSource == null) {
                    log.warn("未找到系统内置数据源，默认使用{}数据源", entry.getKey());
                    sysDataSource = dataSource;
                    // 如果sysDb不存在于dataSourceMap中，添加进去
                    if (!dataSourceMap.containsKey(sysDb)) {
                        dataSourceMap.put(sysDb, dataSource);
                    }
                }
                if (masterDataSource != null && sysDataSource != null) {
                    break;
                }
            }
        }
        
        // 构建数据源映射
        Map<String, DataSource> dsTmp = new HashMap<>(dataSourceMap.size());
        for (Map.Entry<Object, Object> entry : dataSourceMap.entrySet()) {
            dsTmp.put((String) entry.getKey(), (DataSource) entry.getValue());
        }
        
        // 验证主数据源
        AssertUtils.isNull(masterDataSource, "数据库连接对象");
        
        // 配置动态数据源
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.setPrimaryDb(masterDataSource);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        dynamicDataSource.setDynamicDataSources(dsTmp);
        dynamicDataSource.setMybatisProperties(mybatisProperties);
        
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
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("关闭数据源[{}]连接失败！ {}", key, e.getMessage());
                }
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
        
        // 连接池配置
        hikariConfig.setMinimumIdle(Integer.parseInt(dataSourceProperty.getHikari().getMinIdle())); // 最小连接数
        hikariConfig.setMaximumPoolSize(Integer.parseInt(dataSourceProperty.getHikari().getMaxActive())); // 连接池最大连接数
        hikariConfig.setConnectionTimeout(Long.parseLong(dataSourceProperty.getHikari().getMaxWait())); // 获取连接时的最大等待时间，单位为毫秒
        hikariConfig.setMaxLifetime(Long.parseLong(dataSourceProperty.getHikari().getMaxLifetime())); // Hikari属性,控制池中连接的最长生命周期
        hikariConfig.setPoolName(key); // 连接池名称
        
        // 健康检查配置
        hikariConfig.setConnectionTestQuery("SELECT 1"); // 连接测试语句
        hikariConfig.setValidationTimeout(TimeUnit.SECONDS.toMillis(5)); // 验证超时时间
        
        try{
            HikariDataSource dataSource = new HikariDataSource(hikariConfig);
            // 打印监控日志
            if(dataSourceProperty.getHikari().getHikariLog()){
                hikariMonitor(key, dataSource);
            }
            return dataSource;
        }catch (HikariPool.PoolInitializationException e){
            throw new ServerException("创建数据库连接失败!", e.getMessage());
        }
    }

    /**
     * 连接池配置指标监控
     * @param dataSource 连接对象
     */
    private void hikariMonitor(String name, HikariDataSource dataSource) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r);
            t.setName("hikari-monitor-" + name);
            t.setDaemon(true);
            return t;
        });
        
        monitorExecutors.add(executor);
        
        executor.scheduleAtFixedRate(() -> {
            if (isShutdown.get()) {
                executor.shutdown();
                return;
            }
            
            try {
                HikariPoolMXBean hikariPoolMXBean = dataSource.getHikariPoolMXBean();
                if (null != hikariPoolMXBean) {
                    log.info("{} - 连接总数:{}, 活跃连接数:{}, 空闲连接数:{}, 等待连接数:{}",
                            name,
                            hikariPoolMXBean.getTotalConnections(),
                            hikariPoolMXBean.getActiveConnections(),
                            hikariPoolMXBean.getIdleConnections(),
                            hikariPoolMXBean.getThreadsAwaitingConnection());
                }
            } catch (Exception e) {
                log.error("监控数据源[{}]连接池失败！ {}", name, e.getMessage());
            }
        }, 10, 30, TimeUnit.SECONDS);
    }
    
    /**
     * 关闭所有监控线程池
     */
    public void shutdown() {
        if (isShutdown.compareAndSet(false, true)) {
            log.info("关闭数据源监控线程池...");
            for (ScheduledExecutorService executor : monitorExecutors) {
                try {
                    executor.shutdown();
                    if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    log.error("关闭监控线程池时发生中断: {}", e.getMessage());
                    executor.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
            monitorExecutors.clear();
            log.info("数据源监控线程池已关闭");
        }
    }
    
    /**
     * 健康检查数据源连接
     * @param dataSource 数据源
     * @param key 数据源名称
     * @return 是否健康
     */
    public boolean healthCheck(DataSource dataSource, String key) {
        Connection connection = null;
        try {
            log.debug("健康检查数据源[{}]连接", key);
            connection = dataSource.getConnection();
            log.debug("数据源[{}]连接健康", key);
            return true;
        } catch (Exception e) {
            log.error("数据源[{}]连接不健康: {}", key, e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("关闭健康检查连接失败: {}", e.getMessage());
                }
            }
        }
    }
}
