package com.finn.framework.common.properties;

import com.finn.framework.datasource.config.DruidProperty;
import com.finn.framework.datasource.config.HikariProperty;

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

    private HikariProperty hikari;

    private DruidProperty druid;

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

    public HikariProperty getHikari() {
        return hikari;
    }

    public void setHikari(HikariProperty hikari) {
        this.hikari = hikari;
    }

    public DruidProperty getDruid() {
        return druid;
    }

    public void setDruid(DruidProperty druid) {
        this.druid = druid;
    }
}