package com.finn.support.vo;

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

    public TokenVO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
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
}
