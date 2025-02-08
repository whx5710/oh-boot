package com.finn.support.cache;

import com.finn.core.cache.RedisCache;
import com.finn.core.cache.RedisKeys;
import com.finn.core.utils.JsonUtils;
import com.finn.support.entity.SysUserEntity;
import com.finn.support.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
 * 用户 Cache
 * 2024-12-27 18:59:41
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SysUserCache {
    private final RedisCache redisCache;

    private final SysUserMapper sysUserMapper;

    public SysUserCache(RedisCache redisCache, SysUserMapper sysUserMapper){
        this.redisCache = redisCache;
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 获取用户信息,如果redis缓存中有，则从缓存中获取，否则从表里面查询获取
     * @param userId
     * @return
     */
    public SysUserEntity getUser(Long userId) {
        String key = RedisKeys.getUserCacheKey(userId);
        if(redisCache.hasKey(key)){
            return JsonUtils.convertValue(redisCache.get(key), SysUserEntity.class);
        }else{
            SysUserEntity user = sysUserMapper.getById(userId);
            if(user != null && user.getId() != null){
                redisCache.set(key, user, 7200);// 缓存2小时
                return user;
            }else{
                return new SysUserEntity();
            }
        }
    }

}
