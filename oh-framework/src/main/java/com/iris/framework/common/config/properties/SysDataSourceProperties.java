package com.iris.framework.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oh.sys-data-source")
public class SysDataSourceProperties {

    // uri请求前缀
    private String [] prefixUris;

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
