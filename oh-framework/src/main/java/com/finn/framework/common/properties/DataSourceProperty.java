package com.finn.framework.common.properties;

/**
 * 数据库连接属性
 * @author 王小费 whx5710@qq.com
 * @since 2024-08-11
 */
public class DataSourceProperty {
    /**
     * JDBC driver
     */
    private String driverClassName;
    /**
     * JDBC url 地址
     */
    private String url;
    /**
     * JDBC 用户名
     */
    private String username;
    /**
     * JDBC 密码
     */
    private String password;

    /**
     * 解密公匙(如果未设置默认使用全局的)
     */
    private String publicKey;

    /**
     * 连接池建立时创建的初始化连接数
     */
    private String initialSize;
    /**
     * 最小空闲连接数
     */
    private String minIdle;

    /**
     * 最大连接数
     */
    private String maxActive;

    /**
     * 获取连接时的最大等待时间，单位为毫秒。默认30秒
     * 配置了maxWait后，默认启用公平锁
     */
    private String maxWait = "30000";

    /**
     * Hikari属性,控制池中连接的最长生命周期，值0表示无限生命周期，默认30分钟
     */
    private String maxLifetime = "1800000";

    // wall,stat
    private String filters;

    private String connectionProperties;

    private String poolPreparedStatements;

    // 检查连接
    private Boolean checkConnection = false;
    // Hikari 监控日志
    private Boolean hikariLog = false;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(String initialSize) {
        this.initialSize = initialSize;
    }

    public String getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(String minIdle) {
        this.minIdle = minIdle;
    }

    public String getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }

    public String getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(String maxWait) {
        this.maxWait = maxWait;
    }

    public String getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(String maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(String connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public String getPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(String poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public Boolean getCheckConnection() {
        return checkConnection;
    }

    public void setCheckConnection(Boolean checkConnection) {
        this.checkConnection = checkConnection;
    }

    public Boolean getHikariLog() {
        return hikariLog;
    }

    public void setHikariLog(Boolean hikariLog) {
        this.hikariLog = hikariLog;
    }
}