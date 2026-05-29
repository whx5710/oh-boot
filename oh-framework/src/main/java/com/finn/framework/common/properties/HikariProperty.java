package com.finn.framework.common.properties;

/**
 * hikari配置
 * @author 王小费 whx5710@qq.com
 * @since 2026-03-12
 */
public class HikariProperty {
    /**
     * 连接池中保持的最小空闲连接数
     */
    private Integer minimumIdle;
    /**
     * 连接池中允许的最大连接数
     */
    private Integer maximumPoolSize;
    /**
     * 接超时时间 (毫秒) (默认: 30000)
     */
    private Long connectionTimeout = 30000L;
    /**
     * Hikari属性,控制池中连接的最长生命周期，值0表示无限生命周期，默认30分钟
     */
    private Long maxLifetime = 1800000L;
    /**
     * 连接空闲超时时间 (毫秒) (默认: 600000)
     */
    private Long idleTimeout = 600000L;
    /**
     * 连接测试查询语句
     * 默认SELECT 1
     */
    private String connectionTestQuery = "SELECT 1";
    /**
     * 自动提交 (默认: true)
     */
    private Boolean autoCommit;
    /**
     * 验证超时时间，默认5秒
     */
    private long validationTimeout = 5000;
    /**
     * 是否隔离自动提交事务 (默认: false)
     */
    private Boolean isolateInternalQueries = false;

    /**
     * Hikari 监控日志
     */
    private Boolean hikariLog = false;
    /**
     * 慢查询阈值（毫秒）
     */
    private Long slowThreshold = 500L;
    /**
     * 初始化时是否检查连接，默认false
     */
    private Boolean checkConnection = false;

    public Integer getMinimumIdle() {
        return minimumIdle;
    }

    public void setMinimumIdle(Integer minimumIdle) {
        this.minimumIdle = minimumIdle;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Long getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(Long maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public Long getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public String getConnectionTestQuery() {
        return connectionTestQuery;
    }

    public void setConnectionTestQuery(String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }

    public Boolean getAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(Boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public long getValidationTimeout() {
        return validationTimeout;
    }

    public void setValidationTimeout(long validationTimeout) {
        this.validationTimeout = validationTimeout;
    }

    public Boolean getIsolateInternalQueries() {
        return isolateInternalQueries;
    }

    public void setIsolateInternalQueries(Boolean isolateInternalQueries) {
        this.isolateInternalQueries = isolateInternalQueries;
    }

    public Boolean getHikariLog() {
        return hikariLog;
    }

    public void setHikariLog(Boolean hikariLog) {
        this.hikariLog = hikariLog;
    }

    public Long getSlowThreshold() {
        return slowThreshold;
    }

    public void setSlowThreshold(Long slowThreshold) {
        this.slowThreshold = slowThreshold;
    }

    public Boolean getCheckConnection() {
        return checkConnection;
    }

    public void setCheckConnection(Boolean checkConnection) {
        this.checkConnection = checkConnection;
    }
}
