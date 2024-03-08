package com.iris.monitor.vo;

/**
 * Redis Info
 *
 * @author 王小费
 */
public class Cache {

    private String cacheKey;

    private Object cacheValue;

    public Cache(String cacheKey, Object cacheValue) {
        this.cacheKey = cacheKey;
        this.cacheValue = cacheValue;
    }

    public Cache() {
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public Object getCacheValue() {
        return cacheValue;
    }

    public void setCacheValue(Object cacheValue) {
        this.cacheValue = cacheValue;
    }
}
