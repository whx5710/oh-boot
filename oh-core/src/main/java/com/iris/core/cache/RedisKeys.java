package com.iris.core.cache;

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

    /**
     * 刷新token key
     * @param refreshToken
     * @return
     */
    public static String getAccessRefreshTokenKey(String refreshToken) {
        return "sys:accessRefresh:" + refreshToken;
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

    /**
     * 登录失败次数
     * @param loginName 用户
     * @return key
     */
    public static String getAuthCountKey(String loginName){
        return "sys:account:authCount:" + loginName;
    }

    /**
     * mq 报文日志
     * @return sys:msg:log
     */
    public static String getDataMsgKey() {
        return "sys:msg:log";
    }

}
