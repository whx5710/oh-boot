package com.finn.core.cache;

import com.finn.core.utils.DateUtils;
import com.finn.core.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
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

    private final static Logger log = LoggerFactory.getLogger(RedisCache.class);

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCache(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1L;

    private static final Long SUCCESS = 1L;

    /**
     * 缓存数据
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

    /**
     * 缓存数据-长期
     * @param key key
     * @param value 值
     */
    public void set(String key, Object value) {
        set(key, value, NOT_EXPIRE);
    }

    /**
     * 获取数据，重新设置有效时间
     * @param key
     * @param expire
     * @return
     */
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

    /**
     * 判断是否有key
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取key集合
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除缓存
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除缓存
     * @param keys
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 删除key前缀的数据
     * @param key
     */
    public void deleteAll(String key) {
        Set<String> keys = redisTemplate.keys(key + "*");
        //assert keys != null;
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
     * 过期时间
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
     * @param length 每天自增序号的长度
     * @return 格式化的编码
     */
    public String getDayIncrementCode(String prefix, String key, int length) {
        String code = "";
        String formatDay = DateUtils.format(LocalDateTime.now(), "yyyyMMdd");
        Long dayEndTime = getAppointDateTimeMills();
        //当天失效
        long liveTime = (dayEndTime - System.currentTimeMillis()) / 1000;
        Long increment = getIncrement(key, liveTime);
        String sequence = Tools.getSequence(increment, length);
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
        RedisConnectionFactory redisConnectionFactory = redisTemplate.getConnectionFactory();
        if(redisConnectionFactory == null){
            return -1L;
        }
        counter = new RedisAtomicLong(key, redisConnectionFactory);
        long increment =  counter.incrementAndGet(); // 加1后的值
        //初始设置过期时间
        boolean result = (increment == 1) && liveTime > 0;
        if (result) {
            counter.set(1);
            counter.expire(liveTime, TimeUnit.SECONDS);
            // increment = 1L;
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

    /**
     * 加锁
     * @param key
     * @param value
     * @param expireTime
     * @param unit
     * @return
     */
    public Boolean tryLock(String key, String value, long expireTime, TimeUnit unit) {
        try {
            //SET命令返回OK ，则证明获取锁成功
            return redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, unit);
        } catch (Exception e) {
            log.error("加锁失败！{}", e.getMessage());
            return false;
        }
    }

    /**
     * 加锁
     * @param key
     * @param expireTime
     * @param unit
     * @return
     */
    public Boolean tryLock(String key, long expireTime, TimeUnit unit) {
        return tryLock(key, String.valueOf(System.currentTimeMillis()), expireTime, unit);
    }


    /**
     * 解锁
     * @param key
     * @return
     */
    public Boolean unlock(String key) {
        try {
//            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//            RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);
            //redis脚本执行
            //Object result = redisTemplate.execute(redisScript, Collections.singletonList(key), value))
            Object result = redisTemplate.delete(Collections.singletonList(key));
            if (SUCCESS.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            log.error("解锁失败！{}", e.getMessage());
            return false;
        }
        return false;
    }
}