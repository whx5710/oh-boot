package com.finn.sys.service.impl;

import com.finn.core.utils.ExcelUtils;
import com.finn.framework.datasource.utils.Wrapper;
import com.finn.framework.datasource.utils.QueryWrapper;
import com.github.pagehelper.Page;
import com.finn.framework.operatelog.dto.OperateLogDTO;
import com.finn.core.cache.RedisCache;
import com.finn.core.cache.RedisKeys;
import com.finn.core.utils.ExceptionUtils;
import com.finn.core.utils.PageResult;
import com.finn.sys.convert.LogOperateConvert;
import com.finn.sys.entity.LogOperateEntity;
import com.finn.sys.mapper.LogOperateMapper;
import com.finn.sys.query.LogOperateQuery;
import com.finn.sys.service.LogOperateService;
import com.finn.sys.vo.LogOperateVO;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class LogOperateServiceImpl implements LogOperateService {

    private final static Logger log = LoggerFactory.getLogger(LogOperateServiceImpl.class);

    private final RedisCache redisCache;
    private final LogOperateMapper logOperateMapper;

    public LogOperateServiceImpl(RedisCache redisCache, LogOperateMapper logOperateMapper) {
        this.redisCache = redisCache;
        this.logOperateMapper = logOperateMapper;
    }

    @Override
    public PageResult<LogOperateVO> page(LogOperateQuery query) {
        Page<LogOperateEntity> page = logOperateMapper.selectPageByWrapper(getParams(query)
                .page(query.getPageNum(), query.getPageSize()));
        return new PageResult<>(LogOperateConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
    }

    @Override
    public void export(LogOperateQuery query) {
        List<LogOperateEntity> list = logOperateMapper.selectListByWrapper(getParams(query));
        List<LogOperateVO> vo = LogOperateConvert.INSTANCE.convertList(list);
        ExcelUtils.excelExport(LogOperateVO.class, "操作日志", "日志", vo);
    }

    /**
     * 构建SQL
     * @param query
     * @return
     */
    private Wrapper<LogOperateEntity> getParams(LogOperateQuery query){
        return QueryWrapper.of(LogOperateEntity.class)
                .eq(LogOperateEntity::getStatus, query.getStatus())
                .like(LogOperateEntity::getRealName, query.getRealName())
                .like(LogOperateEntity::getModule, query.getModule())
                .like(LogOperateEntity::getReqUri, query.getReqUri())
                .ge(LogOperateEntity::getCreateTime, query.getStartTime())
                .le(LogOperateEntity::getCreateTime, query.getEndTime())
                .eq(LogOperateEntity::getTenantId, query.getTenantId())
                .jointSQL("(module like concat('%',#{keyWords},'%') or name like concat('%',#{keyWords},'%') or req_uri like concat('%',#{keyWords},'%') or real_name like concat('%',#{keyWords},'%'))","keyWords",query.getKeyWords())
                .orderBy("create_time desc");
    }

    /**
     * 启动项目时，从Redis队列获取操作日志并保存
     */
    @PostConstruct
    public void saveLog() {
        ScheduledThreadPoolExecutor scheduledService = new ScheduledThreadPoolExecutor(1);

        // 每隔20秒钟，执行一次
        scheduledService.scheduleWithFixedDelay(() -> {
            try {
                String key = RedisKeys.getLogKey();
                // 每次插入10条
                int count = 10;
                for (int i = 0; i < count; i++) {
                    Object object = redisCache.rightPop(key);
                    if(object == null){
                        return;
                    }
                    OperateLogDTO log = (OperateLogDTO) object;
                    LogOperateEntity entity = LogOperateConvert.INSTANCE.convert(log);
                    logOperateMapper.insert(entity);
                }
            } catch (Exception e) {
                log.error("保存操作日志发生异常：{}", ExceptionUtils.getExceptionMessage(e));
            }
        }, 1, 20, TimeUnit.SECONDS);
    }
}