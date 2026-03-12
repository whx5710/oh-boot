package com.finn.framework.datasource.config;

import com.finn.framework.common.properties.ConnectNumProperty;

/**
 * hikari配置
 * @author 王小费 whx5710@qq.com
 * @since 2026-03-12
 */
public class HikariProperty extends ConnectNumProperty {
    /**
     * Hikari属性,控制池中连接的最长生命周期，值0表示无限生命周期，默认30分钟
     */
    private String maxLifetime = "1800000";

    /**
     * Hikari 监控日志
     */
    private Boolean hikariLog = false;

    public String getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(String maxLifetime) {
        this.maxLifetime = maxLifetime;
    }
    public Boolean getHikariLog() {
        return hikariLog;
    }
    public void setHikariLog(Boolean hikariLog) {
        this.hikariLog = hikariLog;
    }
}
