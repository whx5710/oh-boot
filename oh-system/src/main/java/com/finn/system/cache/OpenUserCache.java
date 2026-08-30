package com.finn.system.cache;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.cache.RedisKeys;
import com.finn.system.entity.OpenUserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公众用户 Cache
 * 2026-08-29 18:59:41
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class OpenUserCache {

    private final RedisCache redisCache;

    public OpenUserCache(RedisCache redisCache){
        this.redisCache = redisCache;
    }

    /**
     * 缓存所有用户
     * @param list
     */
    public void saveList(List<OpenUserEntity> list){
        if(list != null){
            list.forEach(this::saveUser);
        }
    }

    /**
     * 缓存用户
     * @param item
     */
    public void saveUser(OpenUserEntity item){
        if (item == null || item.getId() == null) {
            return;
        }
        String userKey = RedisKeys.PREFIX + "open-user:info:";
        String key = userKey + item.getId();
        if(redisCache.hasKey(key)){
            redisCache.delete(key);
        }
        // 清空多余的数据
        item.setSecretKey(null);
        item.setNote(null);
        item.setUpdater(null);
        item.setUpdateTime(null);
        // 缓存数据
        redisCache.set(key, item.toJson());
    }

}
