package com.iris.framework.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oh.security")
public class SecurityProperties {

    /**
     * token过期时间
     */
    private Long accessTokenExpire;

    /**
     * token刷新时间
     */
    private Long refreshTokenExpire;

    public Long getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setAccessTokenExpire(Long accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }

    public Long getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public void setRefreshTokenExpire(Long refreshTokenExpire) {
        this.refreshTokenExpire = refreshTokenExpire;
    }
}
