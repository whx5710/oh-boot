package com.finn.system.vo;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户Token
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class TokenVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * access_token
     */
    private String accessToken;

    /**
     * refresh_token
     */
    private String refreshToken;

    /**
     * token有效时间
     */
    private Long expiresIn;
    /**
     * 刷新token有效时间
     */
    private Long expireTime;

    /**
     *
     * @param accessToken token值
     * @param refreshToken 刷新token
     * @param expiresIn token有效时间
     * @param expireTime token过期时间
     */
    public TokenVO(String accessToken, String refreshToken, Long expiresIn, Long expireTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.expireTime = expireTime;
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

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }
}
