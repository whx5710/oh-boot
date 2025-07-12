package com.finn.framework.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MQ消费相关
 * @author 王小费 whx5710@qq.com
 */
@Component
@ConfigurationProperties(prefix = "finn.open-api")
public class OpenApiProperties {
    // 1直接保存 2使用MQ异步保存
    private Integer type = 1;
    // Kafka监听是否开启
    private Boolean autoStartUp = false;
    // 缓存时间-秒，0不进行缓存，缓存日志可从redis中读取日志保存到表中
    private Long cacheTime = 0L;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getAutoStartUp() {
        return autoStartUp;
    }

    public void setAutoStartUp(Boolean autoStartUp) {
        this.autoStartUp = autoStartUp;
    }

    public Long getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(Long cacheTime) {
        this.cacheTime = cacheTime;
    }
}
