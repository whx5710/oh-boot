package com.finn.support.cache;

import com.finn.core.cache.RedisCache;
import com.finn.core.cache.RedisKeys;
import com.finn.core.constant.Constant;
import com.finn.core.utils.JsonUtils;
import com.finn.framework.datasource.annotations.Ds;
import com.finn.support.entity.UserEntity;
import com.finn.support.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * 用户 Cache
 * 2024-12-27 18:59:41
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class UserCache {
    private final RedisCache redisCache;

    private final UserMapper userMapper;

    public UserCache(RedisCache redisCache, UserMapper userMapper){
        this.redisCache = redisCache;
        this.userMapper = userMapper;
    }

    /**
     * 获取用户信息,如果redis缓存中有，则从缓存中获取，否则从表里面查询获取
     * @param userId
     * @return
     */
    @Ds(Constant.DYNAMIC_SYS_DB)
    public UserEntity getUser(Long userId) {
        String key = RedisKeys.getUserCacheKey(userId);
        if(redisCache.hasKey(key)){
            return JsonUtils.convertValue(redisCache.get(key), UserEntity.class);
        }else{
            UserEntity user = userMapper.getById(userId);
            if(user != null && user.getId() != null){
                redisCache.set(key, user, 7200);// 缓存2小时
                return user;
            }else{
                return new UserEntity();
            }
        }
    }

}
