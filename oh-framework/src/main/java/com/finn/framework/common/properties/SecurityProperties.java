package com.finn.framework.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "finn.security")
public class SecurityProperties {

    /**
     * token过期时间(秒)
     * 默认2小时
     */
    private Long accessTokenExpire = 7200L;

    /**
     * token刷新时间(秒)
     * 默认12小时
     */
    private Long refreshTokenExpire = 43200L;
    /**
     * 跨域配置，生产环境建议指定具体域名
     */
    private String origins = "";
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

    public String getOrigins() {
        return origins;
    }

    public void setOrigins(String origins) {
        this.origins = origins;
    }
}
