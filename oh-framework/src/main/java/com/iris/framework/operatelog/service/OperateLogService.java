package com.iris.framework.operatelog.service;

import com.iris.framework.operatelog.dto.OperateLogDTO;
import com.iris.core.cache.RedisCache;
import com.iris.core.cache.RedisKeys;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 操作日志服务
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class OperateLogService {
    private final RedisCache redisCache;

    public OperateLogService(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Async
    public void saveLog(OperateLogDTO log) {
        String key = RedisKeys.getLogKey();
        // 保存到Redis队列,存7天
        redisCache.leftPush(key, log, 604800); // 60*60*24*7
    }
}
