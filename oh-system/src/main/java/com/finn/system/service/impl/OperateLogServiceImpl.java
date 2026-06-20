package com.finn.system.service.impl;

import com.finn.framework.datasource.wrapper.Wrapper;
import com.finn.framework.utils.excel.ExcelUtils;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.github.pagehelper.Page;
import com.finn.framework.operatelog.dto.OperateLogDTO;
import com.finn.framework.cache.RedisCache;
import com.finn.framework.cache.RedisKeys;
import com.finn.framework.utils.ExceptionUtils;
import com.finn.framework.entity.PageResult;
import com.finn.system.convert.OperateLogConvert;
import com.finn.system.entity.OperateLogEntity;
import com.finn.system.mapper.OperateLogMapper;
import com.finn.system.query.OperateLogQuery;
import com.finn.system.service.OperateLogService;
import com.finn.system.vo.OperateLogVO;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class OperateLogServiceImpl implements OperateLogService {

    private final static Logger log = LoggerFactory.getLogger(OperateLogServiceImpl.class);

    private final RedisCache redisCache;
    private final OperateLogMapper operateLogMapper;

    public OperateLogServiceImpl(RedisCache redisCache, OperateLogMapper operateLogMapper) {
        this.redisCache = redisCache;
        this.operateLogMapper = operateLogMapper;
    }

    @Override
    public PageResult<OperateLogVO> page(OperateLogQuery query) {
        try (Page<OperateLogEntity> page = operateLogMapper.listByWrapper(getParams(query)
                .page(query.getPageNum(), query.getPageSize()))) {
            return new PageResult<>(OperateLogConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
        }
    }

    @Override
    public void export(OperateLogQuery query) {
        List<OperateLogEntity> list = operateLogMapper.listByWrapper(getParams(query));
        List<OperateLogVO> vo = OperateLogConvert.INSTANCE.convertList(list);
        ExcelUtils.excelExport(OperateLogVO.class, "操作日志", "日志", vo);
    }

    /**
     * 构建SQL
     * @param query
     * @return
     */
    private Wrapper<OperateLogEntity> getParams(OperateLogQuery query){
        return QueryWrapper.of(OperateLogEntity.class)
                .eq(OperateLogEntity::getStatus, query.getStatus())
                .like(OperateLogEntity::getRealName, query.getRealName())
                .like(OperateLogEntity::getModule, query.getModule())
                .like(OperateLogEntity::getReqUri, query.getReqUri())
                .ge(OperateLogEntity::getCreateTime, query.getStartTime())
                .le(OperateLogEntity::getCreateTime, query.getEndTime())
                .jointSQL("(module like concat('%',#{keyWord},'%') or name like concat('%',#{keyWord},'%') or req_uri like concat('%',#{keyWord},'%') or real_name like concat('%',#{keyWord},'%'))","keyWord",query.getKeyWord())
                .orderBy("create_time desc");
    }

    /**
     * 启动项目时，从Redis队列获取操作日志并保存
     */
    @PostConstruct
    public void saveLog() {
        ScheduledThreadPoolExecutor scheduledService = new ScheduledThreadPoolExecutor(1);

        // 每隔120秒钟，执行一次
        scheduledService.scheduleWithFixedDelay(() -> {
            try {
                String key = RedisKeys.getOperateLogKey();
                // 每次插入50条
                int count = 50;
                List<OperateLogEntity> list = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Object object = redisCache.rightPop(key);
                    if(object == null){
                        break;
                    }
                    OperateLogDTO log = (OperateLogDTO) object;
                    OperateLogEntity entity = OperateLogConvert.INSTANCE.convert(log);
                    list.add(entity);
                }
                if(!list.isEmpty()){
                    operateLogMapper.insertBatch(list);
                }
            } catch (Exception e) {
                log.error("保存操作日志发生异常：{}", ExceptionUtils.getExceptionMessage(e));
            }
        }, 1, 120, TimeUnit.SECONDS);
    }
}