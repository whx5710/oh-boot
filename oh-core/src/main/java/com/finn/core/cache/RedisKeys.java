package com.finn.core.cache;

/**
 * Redis Key管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class RedisKeys {

    // 前缀
    public static final String PREFIX = "sys:";

    public static final String ACCESS = PREFIX + "access:";

    /**
     * 验证码Key
     */
    public static String getCaptchaKey(String key) {
        return PREFIX + "captcha:" + key;
    }

    /**
     * accessToken Key
     */
    public static String getAccessTokenKey(String accessToken) {
        return ACCESS + "token:" + accessToken;
    }

    /**
     * 用户信息key<br/>
     * sys:access:user:用户ID:token令牌
     * @param userId
     * @param accessToken
     * @return
     */
    public static String getUserInfoKey(String userId, String accessToken) {
        return ACCESS + "user:" + userId + ":" + accessToken;
    }

    /**
     * 刷新token key
     * @param refreshToken r
     * @return s
     */
    public static String getAccessRefreshTokenKey(String refreshToken) {
        return ACCESS + "refresh:" + refreshToken;
    }

    public static String getLogKey() {
        return PREFIX + "log";
    }

    /**
     * 获取客户端key
     * @param clientId 客户端
     * @return k
     */
    public static String getClientKey(String clientId) {
        return PREFIX + "openapi:" + clientId;
    }

    /**
     * 登录失败次数
     * @param loginName 用户
     * @return key
     */
    public static String getAuthCountKey(String loginName){
        return PREFIX + "account:authCount:" + loginName;
    }

    /**
     * mq 报文日志
     * @return sys:msg:log
     */
    public static String getDataMsgKey() {
        return PREFIX + "msg:log";
    }

    /**
     * 用户信息key
     * @param userId 用户ID
     * @return s
     */
    public static String getUserCacheKey(Long userId){
        return PREFIX + "user:info:" + userId;
    }

    /**
     * 机构key
     * @param orgId 组织机构ID
     * @return s
     */
    public static String getOrgCacheKey(Long orgId){
        return PREFIX + "org:info:" + orgId;
    }
}
