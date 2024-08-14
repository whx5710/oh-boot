package com.iris.framework.datasource.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统数据库连接相关配置
 * @author 王小费 whx5710@qq.com
 */
@Component
@ConfigurationProperties(prefix = SysDataSourceProperties.PREFIX)
public class SysDataSourceProperties {

    public static final String PREFIX = "spring.datasource.sys-data-source";
    private Boolean enable = false;

    // 默认数据源
    private String primary;

    // uri请求前缀
    private String [] prefixUris;

    /**
     * 是否开启数据源过滤拦截
     * @return
     */
    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * uri请求前缀
     * @return
     */
    public String[] getPrefixUris() {
        return prefixUris;
    }

    public void setPrefixUris(String[] prefixUris) {
        this.prefixUris = prefixUris;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }
}
