package com.finn.system.service.impl;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.common.constant.Constant;
import com.finn.framework.common.properties.CommonProperty;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.entity.PageResult;
import com.finn.framework.utils.ExceptionUtils;
import com.finn.framework.utils.JsonUtils;
import com.finn.system.convert.ErrorLogConvert;
import com.finn.system.entity.ErrorLogEntity;
import com.finn.system.mapper.ErrorLogMapper;
import com.finn.system.query.ErrorLogQuery;
import com.finn.system.service.ErrorLogService;
import com.finn.system.vo.ErrorLogVO;
import com.github.pagehelper.Page;
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

    // 60秒执行一次
    int time = 60;

    private final ErrorLogMapper errorLogMapper;
    private final RedisCache redisCache;
    private final CommonProperty commonProperty;

    public ErrorLogServiceImpl(ErrorLogMapper errorLogMapper, RedisCache redisCache, CommonProperty commonProperty) {
        this.errorLogMapper = errorLogMapper;
        this.redisCache = redisCache;
        this.commonProperty = commonProperty;
    }

    /**
     * 启动项目时，从Redis队列获取操作日志并保存
     *
     */
    @PostConstruct
    public void saveLog() {
        ScheduledThreadPoolExecutor scheduledService = new ScheduledThreadPoolExecutor(1);

        // 每次插入条数，每次插入的数据要根据缓存时间、最大缓存量计算
        int minite = Math.toIntExact((commonProperty.getLogCacheTime() / time))<=0?1:Math.toIntExact((commonProperty.getLogCacheTime() / time));
        int count = Math.toIntExact((commonProperty.getLogCacheMaxSize() / minite));

        // 每隔60秒钟，执行一次
        scheduledService.scheduleWithFixedDelay(() -> {
            try {
                String key = PREFIX + "error:msg";
                List<ErrorLogEntity> list = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Object object = redisCache.rightPop(key);
                    if(object == null){
                        break;
                    }
                    ErrorLogEntity e = JsonUtils.parseObject(object.toString(), ErrorLogEntity.class);
                    e.setQueueSize(Math.toIntExact(redisCache.getListSize(key)));
                    list.add(e);
                }
                if(!list.isEmpty()){
                    int i = list.size();
                    if(i == count){
                        for (ErrorLogEntity item : list) {
                            item.setNote("警告：错误日志过多，请排查");
                            long score = Math.min((item.getQueueSize() * 10L / commonProperty.getLogCacheMaxSize()) + 1, 10);
                            item.setScore((int) score);
                        }
                    }
                    long l = errorLogMapper.insertBatch(list);
                    log.debug("保存错误日志{}条", l);
                }
            } catch (Exception e) {
                log.error("保存错误日志发生异常：{}", ExceptionUtils.getExceptionMessage(e));
            }
        }, 1, time, TimeUnit.SECONDS);
    }

    @Override
    public PageResult<ErrorLogVO> page(ErrorLogQuery query) {
        QueryWrapper<ErrorLogEntity> wrapper = QueryWrapper.of(ErrorLogEntity.class);
        wrapper.eq(ErrorLogEntity::getTraceId, query.getTraceId())
                        .like(ErrorLogEntity::getTenantId, query.getTenantId())
                        .le(ErrorLogEntity::getErrTime, query.getEndErrTime())
                        .ge(ErrorLogEntity::getErrTime, query.getStartErrTime())
                .orderBy(ErrorLogEntity::getErrTime, Constant.DESC)
                .page(query.getPageNum(), query.getPageSize());
        try (Page<ErrorLogEntity> page = errorLogMapper.listByWrapper(wrapper)) {
            return new PageResult<>(ErrorLogConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
        }
    }

    @Override
    public void export(ErrorLogQuery query) {

    }

    @Override
    public long delete(List<Long> idList) {
        return 0;
    }

    @Override
    public long deleteByDate(String date) {
        return 0;
    }
}
