package com.finn.framework.cache;

import com.finn.framework.utils.DateUtils;
import com.finn.framework.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        // 优化：使用原子操作，减少网络往返
        if (expire == NOT_EXPIRE) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
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
     * 批量获取多个key的值（使用 MGET 减少网络往返）
     * @param keys key列表
     * @return value列表（与keys顺序一致，key不存在时对应位置为null）
     */
    public List<Object> mGet(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return new ArrayList<>();
        }
        return redisTemplate.opsForValue().multiGet(keys);
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
     * 获取key集合（注意：大数据量时会阻塞Redis，建议使用 scan 方法）
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 使用 SCAN 命令增量迭代获取key，避免阻塞Redis
     * @param pattern 匹配模式
     * @param keyConsumer 对每个key的消费处理
     */
    public void scan(String pattern, Consumer<String> keyConsumer) {
        ScanOptions options = ScanOptions.scanOptions()
                .match(pattern)
                .count(100)
                .build();
        try (Cursor<String> cursor = redisTemplate.scan(options)) {
            while (cursor.hasNext()) {
                keyConsumer.accept(cursor.next());
            }
        } catch (Exception e) {
            log.error("SCAN 操作失败: {}", e.getMessage());
        }
    }

    /**
     * 使用 SCAN 命令增量迭代获取key集合，避免阻塞Redis
     * @param pattern 匹配模式
     * @return key集合
     */
    public Set<String> scanKeys(String pattern) {
        Set<String> keys = new HashSet<>();
        scan(pattern, keys::add);
        return keys;
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
     * 删除key前缀的数据（使用 SCAN 分批删除，避免阻塞 Redis）
     * @param key 前缀
     */
    public void deleteAll(String key) {
        String pattern = key + "*";
        List<String> keysToDelete = new ArrayList<>();
        scan(pattern, k -> {
            keysToDelete.add(k);
            // 每积累 100 个 key 就批量删除一次
            if (keysToDelete.size() >= 100) {
                redisTemplate.delete(keysToDelete);
                keysToDelete.clear();
            }
        });
        // 删除剩余的 key
        if (!keysToDelete.isEmpty()) {
            redisTemplate.delete(keysToDelete);
        }
    }

    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public Map<String, Object> hGetAll(String key) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    /**
     * 批量获取 Hash 中的多个字段值（使用 HMGET 减少网络往返）
     * @param key 键
     * @param fields 字段数组
     * @return 字段值映射（field -> value）
     */
    public Map<String, Object> hMGet(String key, String... fields) {
        if (fields == null || fields.length == 0) {
            return new HashMap<>();
        }
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        List<Object> values = hashOperations.multiGet(key, List.of(fields));
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < fields.length; i++) {
            if (values.get(i) != null) {
                result.put(fields[i], values.get(i));
            }
        }
        return result;
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

    /**
     * 向 Set 中添加元素
     * @param key 键
     * @param member 成员
     * @return 是否添加成功
     */
    public Long sAdd(String key, Object... member) {
        return redisTemplate.opsForSet().add(key, member);
    }

    /**
     * 从 Set 中移除元素
     * @param key 键
     * @param member 成员
     * @return 移除的数量
     */
    public Long sRemove(String key, Object... member) {
        return redisTemplate.opsForSet().remove(key, member);
    }

    /**
     * 获取 Set 中的所有成员
     * @param key 键
     * @return 成员集合
     */
    public Set<String> sMembers(String key) {
        Set<Object> members = redisTemplate.opsForSet().members(key);
        if (members == null) {
            return new HashSet<>();
        }
        Set<String> result = new HashSet<>();
        for (Object member : members) {
            result.add(String.valueOf(member));
        }
        return result;
    }

    /**
     * 判断元素是否在 Set 中
     * @param key 键
     * @param member 成员
     * @return 是否存在
     */
    public Boolean sIsMember(String key, Object member) {
        return redisTemplate.opsForSet().isMember(key, member);
    }

    /**
     * 向 ZSet 中添加元素（带分数）
     * @param key 键
     * @param member 成员
     * @param score 分数（过期时间戳）
     * @return 是否添加成功
     */
    public Boolean zAdd(String key, Object member, double score) {
        return redisTemplate.opsForZSet().add(key, member, score);
    }

    /**
     * 从 ZSet 中移除元素
     * @param key 键
     * @param member 成员
     * @return 移除的数量
     */
    public Long zRemove(String key, Object... member) {
        return redisTemplate.opsForZSet().remove(key, member);
    }

    /**
     * 获取 ZSet 中指定分数范围内的成员（score <= maxScore）
     * @param key 键
     * @param maxScore 最大分数（当前时间戳）
     * @return 成员集合
     */
    public Set<String> zRangeByScore(String key, double maxScore) {
        Set<Object> members = redisTemplate.opsForZSet().rangeByScore(key, 0, maxScore);
        if (members == null) {
            return new HashSet<>();
        }
        Set<String> result = new HashSet<>();
        for (Object member : members) {
            result.add(String.valueOf(member));
        }
        return result;
    }

    /**
     * 删除 ZSet 中指定分数范围内的成员（清理过期数据）
     * @param key 键
     * @param maxScore 最大分数（当前时间戳）
     * @return 删除的数量
     */
    public Long zRemoveRangeByScore(String key, double maxScore) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, 0, maxScore);
    }

    public void leftPush(String key, Object value) {
        leftPush(key, value, NOT_EXPIRE);
    }

    /**
     * 推送数据到list表
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

    /**
     * 从list表拉取数据
     * @param key 键
     * @return obj
     */
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 获取list大小
     * @param key 键
     * @return 大小
     */
    public Long getListSize(String key) {
        Long size = redisTemplate.opsForList().size(key);
        return size != null ? size : 0L;
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
     * 根据模板生成年度自增编号
     * <p>模板占位符：</p>
     * <ul>
     *     <li>#year#：当前年度，如 2026</li>
     *     <li>#num#：原始序列号，不补零</li>
     *     <li>#num:4#：指定长度的序列号，不足前面补零，如 0023</li>
     * </ul>
     * <p>示例：长城综[#year#]广第#num:4#号 → 长城综[2026]广第0023号</p>
     *
     * @param template 模板字符串
     * @return 生成的编号
     */
    public String getCodeByTemplate(String template) {
        int year = LocalDateTime.now().getYear();
        String result = template.replace("#year#", String.valueOf(year));

        Pattern numPattern = Pattern.compile("#num(?::(\\d+))?#");
        Matcher matcher = numPattern.matcher(result);
        if (!matcher.find()) {
            return result;
        }

        String key = "code:tpl:" + year + ":" + template;
        Long seq = getYearIncrement(key, year);
        if (seq <= 0) {
            throw new IllegalStateException("获取年度自增序列号失败");
        }

        matcher.reset();
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String lenStr = matcher.group(1);
            int len = lenStr != null ? Integer.parseInt(lenStr) : 0;
            String seqStr = len > 0 ? Tools.getSequence(seq, len) : String.valueOf(seq);
            matcher.appendReplacement(sb, seqStr);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 获取年度Redis原子自增数据，每年会重新开始
     *
     * @param key  redis key
     * @param year 当前年份
     * @return 自增后的值
     */
    private Long getYearIncrement(String key, int year) {
        RedisConnectionFactory redisConnectionFactory = redisTemplate.getConnectionFactory();
        if (redisConnectionFactory == null) {
            return -1L;
        }
        RedisAtomicLong counter = new RedisAtomicLong(key, redisConnectionFactory);
        long increment = counter.incrementAndGet();
        if (increment == 1L) {
            LocalDateTime nextYear = LocalDateTime.of(year + 1, 1, 1, 0, 0, 0);
            long liveTime = Duration.between(LocalDateTime.now(), nextYear).getSeconds();
            if (liveTime > 0) {
                counter.expire(liveTime, TimeUnit.SECONDS);
            }
        }
        return increment;
    }

    /**
     * 获取redis原子自增数据,每天会重新开始
     * @param key redis key
     * @param liveTime 当天失效还剩余多少秒
     * @return number
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
     * 获取redis原子自增数据，每天会重新开始
     * @param key redis key
     * @return number
     */
    public Long getIncrement(String key) {
        RedisAtomicLong counter = null;
        RedisConnectionFactory redisConnectionFactory = redisTemplate.getConnectionFactory();
        if(redisConnectionFactory == null){
            return -1L;
        }
        counter = new RedisAtomicLong(key, redisConnectionFactory);
        long increment =  counter.incrementAndGet(); // 加1后的值
        //当天失效
        long liveTime = (getAppointDateTimeMills() - System.currentTimeMillis()) / 1000;
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
        ca.set(Calendar.MILLISECOND, 999);
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
     * @param value
     * @return
     */
    public Boolean unlock(String key, String value) {
        try {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
            //redis脚本执行
            Object result = redisTemplate.execute(redisScript, Collections.singletonList(key), value);
            if (SUCCESS.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            log.error("解锁失败 {}", e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * 解锁（兼容旧方法）
     * @param key
     * @return
     */
    public Boolean unlock(String key) {
        try {
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