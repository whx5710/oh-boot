package com.iris.sys.monitor.controller;

import com.iris.framework.cache.RedisCache;
import com.iris.framework.common.utils.Result;
import com.iris.sys.monitor.vo.Cache;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 缓存监控
 *
 * @author 王小费
 */
@RestController
@RequestMapping("monitor/cache")
@Tag(name="缓存监控")
public class CacheController {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedisCache redisCache;

    /**
     * Redis详情
     */
    @GetMapping("info")
    @Operation(summary = "Redis详情")
    @PreAuthorize("hasAuthority('monitor:cache:all')")
    public Result<Map<String, Object>> getInfo() {
        Map<String, Object> result = new HashMap<>();
        // Step 1: 获取Redis详情
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::info);
        result.put("info", info);
        // Step 2: 获取Key的数量
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::dbSize);
        result.put("keyCount", dbSize);
        // Step 3: 获取请求次数
        List<Map<String, Object>> pieList = new ArrayList<>();
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandStats"));
        if (commandStats != null && commandStats.size() != 0) {
            commandStats.stringPropertyNames().forEach(key -> {
                Map<String, Object> data = new HashMap<>();
                String property = commandStats.getProperty(key);
                data.put("name", StringUtils.substring(key, 8));
                data.put("value", StringUtils.substringBetween(property, "calls=", ",use"));
                pieList.add(data);
            });
        }
        result.put("commandStats", pieList);
        return Result.ok(result);
    }

    /**
     * 获取所有的Key
     */
    @Operation(summary = "获取所有的Key")
    @GetMapping("getCacheKeys")
    @PreAuthorize("hasAuthority('monitor:cache:all')")
    public Result<Set<String>> getCacheKeys() {
        Set<String> cacheKeys = redisTemplate.keys("*");
        return Result.ok(cacheKeys);
    }

    /**
     * 获取结构化键下的Key值
     *
     * @param cacheKey
     */
    @Operation(summary = "获取结构化键下的Key值")
    @GetMapping("getCacheKeys/{cacheKey}")
    @PreAuthorize("hasAuthority('monitor:cache:all')")
    public Result<Set<String>> getCacheKeys(@PathVariable String cacheKey) {
        Set<String> cacheKeys = redisTemplate.keys(cacheKey + "*");
        return Result.ok(cacheKeys);
    }

    /**
     * 获取指定键的值
     *
     * @param cacheKey
     */
    @Operation(summary = "获取指定键的值")
    @GetMapping("getCacheValue/{cacheKey}")
    @PreAuthorize("hasAuthority('monitor:server:all')")
    public Result<Cache> getCacheValue(@PathVariable String cacheKey) {
        Object cacheValue = null;
        try{
            cacheValue = redisTemplate.opsForValue().get(cacheKey);
        }catch (RedisSystemException e){
            Map<String,Object> map = redisCache.hGetAll(cacheKey);
            return Result.ok(new Cache(cacheKey, map));
        }
        Cache cache = new Cache(cacheKey, cacheValue);
        return Result.ok(cache);
    }

    /**
     * 删除指定键的缓存
     *
     * @param cacheKey > Key值
     */
    @Operation(summary = "删除指定键的缓存")
    @DeleteMapping("delCacheKey/{cacheKey}")
    @PreAuthorize("hasAuthority('monitor:cache:all')")
    public Result<String> delCacheKey(@PathVariable String cacheKey) {
        boolean flag = redisTemplate.delete(cacheKey);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error(200, "处理失败!");
        }
    }

    /**
     * 删除结构化键下的缓存
     *
     * @param cacheKey > Key值
     */
    @Operation(summary = "删除结构化键下的缓存")
    @DeleteMapping("delCacheKeys/{cacheKey}")
    @PreAuthorize("hasAuthority('monitor:cache:all')")
    public Result<String> delCacheKeys(@PathVariable String cacheKey) {
        Collection<String> cacheKeys = redisTemplate.keys(cacheKey + "*");
        redisTemplate.delete(cacheKeys);
        return Result.ok();
    }

    /**
     * 删除全部缓存
     */
    @Operation(summary = "删除全部缓存")
    @DeleteMapping("delCacheAll")
    @PreAuthorize("hasAuthority('monitor:cache:all')")
    public Result<String> delCacheAll() {
        Collection<String> cacheKeys = redisTemplate.keys("*");
        redisTemplate.delete(cacheKeys);
        return Result.ok();
    }

}
