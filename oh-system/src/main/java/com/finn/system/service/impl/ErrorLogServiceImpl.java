package com.finn.system.service.impl;

import com.finn.framework.cache.ErrorLogCache;
import com.finn.framework.common.constant.Constant;
import com.finn.framework.common.properties.CommonProperty;
import com.finn.framework.datasource.wrapper.DeleteWrapper;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.entity.HashDto;
import com.finn.framework.entity.PageResult;
import com.finn.common.utils.AssertUtils;
import com.finn.common.utils.DateUtils;
import com.finn.common.utils.ExceptionUtils;
import com.finn.common.utils.JsonUtils;
import com.finn.common.utils.NamedDaemonThreadFactory;
import com.finn.framework.utils.excel.ExcelUtils;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    final int time = 60;

    private final ErrorLogMapper errorLogMapper;
    private final ErrorLogCache errorLogCache;
    private final CommonProperty commonProperty;

    public ErrorLogServiceImpl(ErrorLogMapper errorLogMapper, ErrorLogCache errorLogCache, CommonProperty commonProperty) {
        this.errorLogMapper = errorLogMapper;
        this.errorLogCache = errorLogCache;
        this.commonProperty = commonProperty;
    }

    /**
     * 启动项目时，从Redis队列获取操作日志并保存
     * 60秒一次，从redis中获取错误日志并保存
     */
    @PostConstruct
    public void saveLog() {
        ScheduledThreadPoolExecutor scheduledService = new ScheduledThreadPoolExecutor(1, new NamedDaemonThreadFactory("error-log-save"));

        // 每次插入条数，每次插入的数据要根据缓存时间、最大缓存量计算
        int minite = Math.toIntExact((commonProperty.getLogCacheTime() / time))<=0?1:Math.toIntExact((commonProperty.getLogCacheTime() / time));
        int count = Math.toIntExact((commonProperty.getLogCacheMaxSize() / minite));

        // 每隔60秒钟，执行一次
        scheduledService.scheduleWithFixedDelay(() -> {
            try {
                List<HashDto> list = errorLogCache.getLog(count);
                if(!list.isEmpty()){
                    int i = list.size();
                    List<ErrorLogEntity> errorLogEntities = new ArrayList<>(i);
                    for(HashDto hashDto: list){
                        ErrorLogEntity e = JsonUtils.parseObject(hashDto.getStr("data"), ErrorLogEntity.class);
                        e.setQueueSize(hashDto.getInt("cacheSize"));
                        BigDecimal score = BigDecimal.valueOf(Math.min((e.getQueueSize() * 5L / commonProperty.getLogCacheMaxSize()) + 1, 5));
                        e.setScore(score);
                        if(i == count){
                            e.setNote("警告：错误日志过多，请排查");
                        }
                        if(score.compareTo(new BigDecimal("4")) > 0){
                            e.setNote("警告：错误日志极多，影响系统性能");
                        }
                        errorLogEntities.add(e);
                    }
                    long l = errorLogMapper.insertBatch(errorLogEntities);
                    log.debug("保存错误日志{}条", l);
                }
            } catch (Exception e) {
                log.error("保存错误日志发生异常：{}", ExceptionUtils.getExceptionMessage(e));
            }
        }, 10, time, TimeUnit.SECONDS);
    }

    @Override
    public PageResult<ErrorLogVO> page(ErrorLogQuery query) {
        QueryWrapper<ErrorLogEntity> wrapper = getQueryWrapper(query);
        wrapper.page(query.getPageNum(), query.getPageSize());
        try (Page<ErrorLogEntity> page = errorLogMapper.listByWrapper(wrapper)) {
            return new PageResult<>(ErrorLogConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
        }
    }

    /**
     * 导出
     * @param query
     */
    @Override
    public void export(ErrorLogQuery query) {
        QueryWrapper<ErrorLogEntity> wrapper = getQueryWrapper(query);
        List<ErrorLogEntity> list = errorLogMapper.listByWrapper(wrapper);
        ExcelUtils.excelExport(ErrorLogVO.class, "错误日志", "日志", ErrorLogConvert.INSTANCE.convertList(list));
    }

    @Override
    public long delete(List<Long> idList) {
        AssertUtils.isNull(idList, "ID集合");
        DeleteWrapper<ErrorLogEntity> deleteWrapper = DeleteWrapper.of(ErrorLogEntity.class);
        deleteWrapper.in(ErrorLogEntity::getId, idList);
        return errorLogMapper.deleteByWrapper(deleteWrapper);
    }

    @Override
    public long deleteByDate(String date) {
        DeleteWrapper<ErrorLogEntity> deleteWrapper = DeleteWrapper.of(ErrorLogEntity.class);
        deleteWrapper.le(ErrorLogEntity::getErrTime, DateUtils.parseLocalDateTime(date));
        return errorLogMapper.deleteByWrapper(deleteWrapper);
    }

    /**
     * 构建查询，不包括分页
     * @param query
     * @return
     */
    private QueryWrapper<ErrorLogEntity> getQueryWrapper(ErrorLogQuery query){
        QueryWrapper<ErrorLogEntity> wrapper = QueryWrapper.of(ErrorLogEntity.class);
        wrapper.eq(ErrorLogEntity::getTraceId, query.getTraceId())
                .le(ErrorLogEntity::getErrTime, query.getEndErrTime())
                .ge(ErrorLogEntity::getErrTime, query.getStartErrTime())
                .orderBy(ErrorLogEntity::getErrTime, Constant.DESC);
        if(query.getKeyWord() != null && !query.getKeyWord().isEmpty()){
            wrapper.jointSQL("(msg like concat('%', #{keyWord}, '%') or stack_info like concat('%', #{keyWord}, '%')" +
                            " or trace_id like concat('%', #{keyWord}, '%'))",
                    "keyWord", query.getKeyWord());
        }
        return wrapper;
    }
}
