package com.finn.framework.common.properties;

/**
 * 数据库连接属性
 * @author 王小费 whx5710@qq.com
 * @since 2026-03-12
 */
public class ConnectNumProperty {
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
     * 检查连接
     */
    private Boolean checkConnection = false;
    /**
     * 连接测试查询语句
     * 默认SELECT 1
     */
    private String connectionTestQuery = "SELECT 1";

    /**
     * 验证超时时间，默认5秒
     */
    private long validationTimeout = 5000;

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

    public Boolean getCheckConnection() {
        return checkConnection;
    }

    public void setCheckConnection(Boolean checkConnection) {
        this.checkConnection = checkConnection;
    }

    public String getConnectionTestQuery() {
        return connectionTestQuery;
    }

    public void setConnectionTestQuery(String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }

    public long getValidationTimeout() {
        return validationTimeout;
    }

    public void setValidationTimeout(long validationTimeout) {
        this.validationTimeout = validationTimeout;
    }
}
