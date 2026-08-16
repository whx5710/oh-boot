package com.finn.system.cache;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.cache.RedisKeys;
import com.finn.system.entity.ParamsEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 参数管理 Cache
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class ParamsCache {
    private final RedisCache redisCache;

    /**
     * 参数管理 KEY
     */
    private final String SYSTEM_PARAMS_KEY = RedisKeys.PREFIX + "params";

    public ParamsCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
     * 保存参数列表到缓存
     *
     * @param list 参数列表
     */
    public void saveList(List<ParamsEntity> list) {
        redisCache.delete(SYSTEM_PARAMS_KEY);
        if (list == null || list.isEmpty()) {
            return;
        }
        
        // list 转成 map
        Map<String, Object> map = list.stream().collect(Collectors.toMap(ParamsEntity::getParamKey, ParamsEntity::getParamValue));

        redisCache.hMSet(SYSTEM_PARAMS_KEY, map, RedisCache.NOT_EXPIRE);
    }

    /**
     * 删除缓存中的全部参数列表
     */
    public void delList() {
        redisCache.delete(SYSTEM_PARAMS_KEY);
    }

    /**
     * 根据参数键，获取参数值
     *
     * @param paramKey 参数键
     */
    public String get(String paramKey) {
        Object obj = redisCache.hGet(SYSTEM_PARAMS_KEY, paramKey);
        if(obj == null){
            return null;
        }
        return String.valueOf(obj);
    }

    /**
     * 批量获取参数值（使用 HMGET 减少网络往返）
     *
     * @param paramKeys 参数键列表
     * @return 参数键值对
     */
    public Map<String, Object> mGet(List<String> paramKeys) {
        if (paramKeys == null || paramKeys.isEmpty()) {
            return new HashMap<>();
        }
        return redisCache.hMGet(SYSTEM_PARAMS_KEY, paramKeys.toArray(new String[0]));
    }

    /**
     * 根据参数键，获取参数值
     *
     * @param paramKey   参数键
     * @param paramValue 参数值
     */
    public void save(String paramKey, String paramValue) {
        redisCache.hSet(SYSTEM_PARAMS_KEY, paramKey, paramValue);
    }

    /**
     * 根据参数键，删除参数值
     *
     * @param paramKey 参数键
     */
    public void del(String... paramKey) {
        redisCache.hDel(SYSTEM_PARAMS_KEY, paramKey);
    }
}
