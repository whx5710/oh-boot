package com.iris.flow.config;

import com.iris.core.cache.RedisCache;
import org.camunda.bpm.engine.impl.cfg.IdGenerator;
import org.springframework.stereotype.Component;

/**
 * 自定义ID
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-05
 *
 */
@Component
public class CustomUuidGenerator implements IdGenerator {

    private final RedisCache redisCache;

    public CustomUuidGenerator(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    public String getNextId() {
        //自己有需要可以在这里返回自定义生成的ID,生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
        //return IdUtil.simpleUUID();
        return redisCache.getDayIncrementCode("", "oh.flow.index", 10);
    }
}
