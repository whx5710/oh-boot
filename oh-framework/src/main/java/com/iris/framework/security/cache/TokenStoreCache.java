package com.iris.framework.security.cache;

import cn.hutool.core.collection.ListUtil;
import com.iris.framework.security.user.UserDetail;
import com.iris.framework.common.cache.RedisCache;
import com.iris.framework.common.cache.RedisKeys;
import org.springframework.stereotype.Component;

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

    public TokenStoreCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
     * 保存用户信息到redis中
     * @param accessToken
     * @param user
     */
    public void saveUser(String accessToken, UserDetail user) {
        String key = RedisKeys.getAccessTokenKey(accessToken);
        redisCache.set(key, user);
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

    public List<String> getUserKeyList() {
        String pattern = RedisKeys.getAccessTokenKey("*");
        Set<String> sets = redisCache.keys(pattern);

        return ListUtil.toList(sets);
    }
}
