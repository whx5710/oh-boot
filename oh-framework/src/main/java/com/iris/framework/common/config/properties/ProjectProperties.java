package com.iris.framework.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置文件，项目属性
 */
@Component
@ConfigurationProperties(prefix = "oh.project")
public class ProjectProperties {
    /**
     * 名称
     */
    String name;
    /**
     * 应用名称
     */
    String appName;
    /**
     * 版本
     */
    String version;
    /**
     * 描述
     */
    String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
