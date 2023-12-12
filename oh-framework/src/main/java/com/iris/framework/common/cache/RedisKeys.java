package com.iris.framework.common.cache;

/**
 * Redis Key管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class RedisKeys {

    /**
     * 验证码Key
     */
    public static String getCaptchaKey(String key) {
        return "sys:captcha:" + key;
    }

    /**
     * accessToken Key
     */
    public static String getAccessTokenKey(String accessToken) {
        return "sys:access:" + accessToken;
    }

    public static String getLogKey() {
        return "sys:log";
    }

    /**
     * 获取客户端key
     * @param clientId 客户端
     * @return k
     */
    public static String getClientKey(String clientId) {
        return "sys:openapi:" + clientId;
    }

}
