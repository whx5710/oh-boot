package com.iris.framework.common.properties;

import com.iris.core.constant.Constant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统数据库连接相关配置
 * @author 王小费 whx5710@qq.com
 */
@Component
@ConfigurationProperties(prefix = SysDataSourceProperties.PREFIX)
public class SysDataSourceProperties {

    public static final String PREFIX = "iris.datasource.sys-data-source";
    private Boolean enable = false;

    // 主数据源或者数据源组,默认 masterDb
    private String primary = Constant.MASTER_DB;

    // 系统管理的数据源，用于基础管理的库，默认 sysDb
    private String sysDefault = Constant.SYS_DB;

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

    /**
     * 主数据源或者数据源组,默认 masterDb
     * @return
     */
    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    /**
     * 系统管理的数据源，用于基础管理的库，默认 sysDb
     * @return
     */
    public String getSysDefault() {
        return sysDefault;
    }

    public void setSysDefault(String sysDefault) {
        this.sysDefault = sysDefault;
    }
}
