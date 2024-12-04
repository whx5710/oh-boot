package com.iris.core.cache;

import com.iris.core.utils.DateUtils;
import com.iris.core.utils.IrisTools;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis Cache
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Component
public class RedisCache {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCache(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1L;

    /**
     *
     * @param key key
     * @param value 值
     * @param expire 时长-秒
     */
    public void set(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key, value);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    public void set(String key, Object value) {
        set(key, value, NOT_EXPIRE);
    }

    public Object get(String key, long expire) {
        Object value = redisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
        return value;
    }

    /**
     * 根据key获取对象
     * @param key
     * @return
     */
    public Object get(String key) {
        return get(key, NOT_EXPIRE);
    }

    /**
     * 增量 自增
     * @param key
     * @return
     */
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 删除key前缀的数据
     * @param key
     */
    public void deleteAll(String key) {
        Set<String> keys = redisTemplate.keys(key + "*");
        assert keys != null;
        redisTemplate.delete(keys);
    }

    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public Map<String, Object> hGetAll(String key) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    public void hMSet(String key, Map<String, Object> map) {
        hMSet(key, map, NOT_EXPIRE);
    }

    public void hMSet(String key, Map<String, Object> map, long expire) {
        redisTemplate.opsForHash().putAll(key, map);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    public void hSet(String key, String field, Object value) {
        hSet(key, field, value, NOT_EXPIRE);
    }

    public void hSet(String key, String field, Object value, long expire) {
        redisTemplate.opsForHash().put(key, field, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    public void expireAt(String key, Date expire) {
        redisTemplate.expireAt(key, expire);
    }

    /**
     * 获取过期时间
     * @param key 键
     * @return 秒
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     *
     * @param key
     * @param expire 时长-秒
     */
    public void expire(String key, long expire) {
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public void hDel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    public void leftPush(String key, Object value) {
        leftPush(key, value, NOT_EXPIRE);
    }

    /**
     *
     * @param key
     * @param value
     * @param expire 时长-秒
     */
    public void leftPush(String key, Object value, long expire) {
        redisTemplate.opsForList().leftPush(key, value);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }


    /**
     * redis key的层级不能超过3层（）
     * 根据前缀+日期+每天的自增编号例如（WF2021041411200001）
     *
     * @param prefix 前缀
     * @param key redis键
     * @param length 长度
     * @return 格式化的编码
     */
    public String getDayIncrementCode(String prefix, String key, int length) {
        String code = "";
        String formatDay = DateUtils.format(LocalDateTime.now(), "yyyyMMdd");
        Long dayEndTime = getAppointDateTimeMills();
        //当天失效
        long liveTime = (dayEndTime - System.currentTimeMillis()) / 1000;
        Long increment = getIncrement(key, liveTime);
        String sequence = IrisTools.getSequence(increment, length);
        if (prefix != null) {
            code = code + prefix;
        }
        code = code + formatDay + sequence;

        return code;
    }

    /**
     * 获取redis原子自增数据
     *
     * @param key
     * @param liveTime
     * @return
     */
    public Long getIncrement(String key, long liveTime) {
        RedisAtomicLong counter = null;
        counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        // Long increment =  counter.incrementAndGet(); // 加1后的值
        Long increment =  counter.getAndIncrement(); // 加1前的值
        //初始设置过期时间
        boolean result = (null == increment || increment.longValue() == 0) && liveTime > 0;
        if (result) {
            counter.set(1);
            counter.expire(liveTime, TimeUnit.SECONDS);
            increment = 1L;
        }
        return increment;
    }

    /**
     * 获取指定时间毫秒值
     *
     * @return
     */
    private static Long getAppointDateTimeMills() {
        Calendar ca = Calendar.getInstance();
        //失效的时间
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        return ca.getTimeInMillis();
    }

}