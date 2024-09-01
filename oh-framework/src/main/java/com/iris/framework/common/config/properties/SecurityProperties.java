package com.iris.framework.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "iris.security")
public class SecurityProperties {

    /**
     * token过期时间
     */
    private Long accessTokenExpire;

    /**
     * token刷新时间
     */
    private Long refreshTokenExpire;
    /**
     * 多少次鉴权失败锁定，0表示不开启
     */
    private int authCount = 0;
    /**
     * 账号锁定时间(秒)
     */
    private Long lockTime = 0L;
    /**
     * 忽略路径
     */
    private List<String> ignoreUrls;

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

    /**
     * 多少次鉴权失败锁定，0表示不开启
     * @return 次数
     */
    public int getAuthCount() {
        return authCount;
    }

    public void setAuthCount(int authCount) {
        this.authCount = authCount;
    }

    /**
     * 锁定时间
     * @return long
     */
    public Long getLockTime() {
        return lockTime;
    }

    public void setLockTime(Long lockTime) {
        this.lockTime = lockTime;
    }

    public List<String> getIgnoreUrls() {
        return ignoreUrls;
    }

    public void setIgnoreUrls(List<String> ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }
}
