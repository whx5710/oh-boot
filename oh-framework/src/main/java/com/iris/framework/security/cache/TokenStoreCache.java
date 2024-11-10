package com.iris.framework.security.cache;

import com.iris.core.cache.RedisCache;
import com.iris.core.cache.RedisKeys;
import com.iris.framework.common.properties.SecurityProperties;
import com.iris.framework.security.user.UserDetail;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 认证 Cache
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Component
public class TokenStoreCache {
    private final RedisCache redisCache;

    private final SecurityProperties securityProperties;

    public TokenStoreCache(RedisCache redisCache, SecurityProperties securityProperties) {
        this.redisCache = redisCache;
        this.securityProperties = securityProperties;
    }

    /**
     * 保存用户信息到redis中
     * @param accessToken
     * @param user
     */
    public void saveUser(String accessToken, String refreshToken, UserDetail user) {
        String key = RedisKeys.getAccessTokenKey(accessToken);
        redisCache.set(key, user);
        // 刷新token
        String refreshKey = RedisKeys.getAccessRefreshTokenKey(refreshToken);
        redisCache.set(refreshKey, user, securityProperties.getRefreshTokenExpire());
    }

    /**
     * 根据token获取用户
     * @param accessToken
     * @return
     */
    public UserDetail getUser(String accessToken) {
        String key = RedisKeys.getAccessTokenKey(accessToken);
        return (UserDetail) redisCache.get(key);
    }

    /**
     * 根据token，删除redis
     * @param accessToken
     */
    public void deleteUser(String accessToken) {
        String key = RedisKeys.getAccessTokenKey(accessToken);
        redisCache.delete(key);
    }

    /**
     * 获取所有用户token的key
     * @return list
     */
    public List<String> getUserKeyList() {
        String pattern = RedisKeys.getAccessTokenKey("*");
        Set<String> sets = redisCache.keys(pattern);
        List<String> list = new ArrayList<>();
        if(sets != null){
            list.addAll(sets);
        }
        return list;
    }
}
