package com.finn.system.cache;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.cache.RedisKeys;
import com.finn.framework.utils.JsonUtils;
import com.finn.system.entity.UserEntity;
import com.finn.system.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public UserEntity getUser(Long userId) {
        String key = RedisKeys.getUserCacheKey(userId);
        if(redisCache.hasKey(key)){
            return JsonUtils.parseObject(redisCache.get(key).toString(), UserEntity.class);
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

    /**
     * 缓存所有用户
     * @param list
     */
    public void saveList(List<UserEntity> list){
        if(list != null){
            list.forEach(item -> {
                String key = RedisKeys.getUserCacheKey(item.getId());
                if(redisCache.hasKey(key)){
                    redisCache.delete(key);
                }
                // 缓存数据
                redisCache.set(key, item.toJson());
            });
        }
    }

}
