package com.finn.files.vo;

import java.io.Serial;
import java.io.Serializable;

/**
 * 预签名 URL 视图对象
 */
public class PresignedUrlVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 预签名 URL
     */
    private String url;

    /**
     * 文件 key
     */
    private String key;

    /**
     * 过期时间（秒）
     */
    private Long expirationSeconds;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getExpirationSeconds() {
        return expirationSeconds;
    }

    public void setExpirationSeconds(Long expirationSeconds) {
        this.expirationSeconds = expirationSeconds;
    }
}
