package com.finn.system.service.impl;

import com.finn.core.utils.PageResult;
import com.finn.system.convert.ScheduleJobLogConvert;
import com.finn.system.entity.ScheduleJobLogEntity;
import com.finn.system.mapper.ScheduleJobMapper;
import com.finn.system.query.ScheduleJobLogQuery;
import com.finn.system.service.ScheduleJobLogService;
import com.finn.system.vo.ScheduleJobLogVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {

    private final ScheduleJobMapper scheduleJobMapper;

    public ScheduleJobLogServiceImpl(ScheduleJobMapper scheduleJobMapper){
        this.scheduleJobMapper = scheduleJobMapper;
    }
    @Override
    public PageResult<ScheduleJobLogVO> page(ScheduleJobLogQuery query) {
        List<ScheduleJobLogEntity> list = scheduleJobMapper.getLogList(query);
        return new PageResult<>(ScheduleJobLogConvert.INSTANCE.convertList(list), query.getTotal());
    }

    @Override
    public void delete(List<Long> idList) {
        scheduleJobMapper.deleteLogByIds(idList);
    }

    @Override
    public int save(ScheduleJobLogEntity entity) {
        return scheduleJobMapper.insertJobLog(entity);
    }

    @Override
    public ScheduleJobLogEntity getById(Long id) {
        return scheduleJobMapper.getLogById(id);
    }

}