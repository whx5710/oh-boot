package com.finn.system.service.impl;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.utils.ExceptionUtils;
import com.finn.framework.utils.JsonUtils;
import com.finn.system.entity.ErrorLogEntity;
import com.finn.system.mapper.ErrorLogMapper;
import com.finn.system.service.ErrorLogService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.finn.framework.cache.RedisKeys.PREFIX;

/**
 * 系统错误日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-04-29 18:38:22
 *
 */
@Service
public class ErrorLogServiceImpl implements ErrorLogService {

    private final static Logger log = LoggerFactory.getLogger(ErrorLogServiceImpl.class);

    private final ErrorLogMapper errorLogMapper;
    private final RedisCache redisCache;
    public ErrorLogServiceImpl(ErrorLogMapper errorLogMapper, RedisCache redisCache) {
        this.errorLogMapper = errorLogMapper;
        this.redisCache = redisCache;
    }

    /**
     * 启动项目时，从Redis队列获取操作日志并保存
     */
    @PostConstruct
    public void saveLog() {
        ScheduledThreadPoolExecutor scheduledService = new ScheduledThreadPoolExecutor(1);

        // 每隔60秒钟，执行一次
        scheduledService.scheduleWithFixedDelay(() -> {
            try {
                String key = PREFIX + "error:msg";
                // 每次插入50条
                int count = 50;
                List<ErrorLogEntity> list = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Object object = redisCache.rightPop(key);
                    if(object == null){
                        break;
                    }
                    ErrorLogEntity e = JsonUtils.parseObject(object.toString(), ErrorLogEntity.class);
                    list.add(e);
                }
                if(!list.isEmpty()){
                    int i = list.size();
                    log.debug("保存错误日志{}条", i);
                    if(i == count){
                        list.forEach(item -> {
                            item.setNote("警告：错误日志过多，请排查");
                        });
                    }
                    errorLogMapper.insertBatch(list);
                }
            } catch (Exception e) {
                log.error("保存错误日志发生异常：{}", ExceptionUtils.getExceptionMessage(e));
            }
        }, 1, 60, TimeUnit.SECONDS);
    }
}
