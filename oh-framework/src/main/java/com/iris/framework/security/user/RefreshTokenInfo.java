package com.iris.framework.security.user;

import com.iris.core.entity.BaseUserEntity;

/**
 * 刷新token信息
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class RefreshTokenInfo extends BaseUserEntity {

    private String accessToken;

    private String refreshToken;

    private String ip;

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
}
