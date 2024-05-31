package com.iris.framework.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oh.sys-data-source")
public class SysDataSourceProperties {

    private Boolean enable = false;

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
}
