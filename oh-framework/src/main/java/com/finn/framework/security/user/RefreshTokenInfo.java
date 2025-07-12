package com.finn.framework.security.user;

import com.finn.core.entity.SuperEntity;

import java.io.Serial;
import java.io.Serializable;

/**
 * 刷新token信息
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class RefreshTokenInfo extends SuperEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String realName;
    /**
     * token
     */
    private String accessToken;
    /**
     * 刷新token
     */
    private String refreshToken;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 过期时间-秒 默认2小时
     */
    private Long expiresIn = 7200L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
