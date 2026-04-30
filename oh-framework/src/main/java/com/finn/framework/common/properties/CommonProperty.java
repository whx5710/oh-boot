package com.finn.framework.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 框架公共配置
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-04-30 18:45:22
 *
 */
@Component
@ConfigurationProperties(prefix = "finn.common")
public class CommonProperty {

    /**
     * 是否开启系统错误日志记录 false
     */
    private Boolean errLogCache = false;
    /**
     * 默认缓存时间 300秒
     */
    private Long logCacheTime = 300L;
    /**
     * 默认缓存最大数量 1000
     */
    private Long logCacheMaxSize = 1000L;

    public Boolean getErrLogCache() {
        return errLogCache;
    }

    public void setErrLogCache(Boolean errLogCache) {
        this.errLogCache = errLogCache;
    }

    public Long getLogCacheTime() {
        return logCacheTime;
    }

    public void setLogCacheTime(Long logCacheTime) {
        this.logCacheTime = logCacheTime;
    }

    public Long getLogCacheMaxSize() {
        return logCacheMaxSize;
    }

    public void setLogCacheMaxSize(Long logCacheMaxSize) {
        this.logCacheMaxSize = logCacheMaxSize;
    }
}
