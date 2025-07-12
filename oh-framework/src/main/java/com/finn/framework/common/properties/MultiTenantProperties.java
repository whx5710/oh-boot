package com.finn.framework.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 租户相关配置
 * @author 王小费 whx5710@qq.com
 */
@Component
@ConfigurationProperties(prefix = MultiTenantProperties.PREFIX)
public class MultiTenantProperties {
    public static final String PREFIX = "finn.multi-tenant";

    /**
     * 忽略的系统内置表： sys_params,sys_version_info,sys_menu,sys_role_menu,sys_user_role,sys_user_post
     * 其他的在配置文件中进行配置
     */
    public static final Set<String> tables = new HashSet<>(Arrays.asList("sys_params", "sys_version_info", "sys_menu", "sys_role_menu", "sys_user_role", "sys_user_post"));

    private boolean enable = true;
    // 当前数据库的方言，默认mysql
    private String dialect = "mysql";
    // 多租户字段名称
    private String tenantIdField = "tenant_id";
    // 需要识别多租户字段的表名称的正则表达式
    private String tablePattern;
    // 需要识别多租户字段的表名称列表
    private Set<String> ignoreTable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * 当前数据库的方言,默认mysql
     * @return
     */
    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    /**
     * 多租户字段名称，默认tenant_id
     * @return
     */
    public String getTenantIdField() {
        return tenantIdField;
    }

    public void setTenantIdField(String tenantIdField) {
        this.tenantIdField = tenantIdField;
    }

    public String getTablePattern() {
        return tablePattern;
    }

    public void setTablePattern(String tablePattern) {
        this.tablePattern = tablePattern;
    }

    public Set<String> getIgnoreTable() {
        return ignoreTable;
    }

    /**
     * 忽略的表
     * @param ignoreTable
     */
    public void setIgnoreTable(Set<String> ignoreTable) {
        ignoreTable.addAll(tables); // 拼接系统内置的表
        this.ignoreTable = ignoreTable;
    }
}
