package com.finn.sys.quartz.service.impl;

import com.finn.core.utils.PageResult;
import com.finn.sys.quartz.convert.ScheduleJobLogConvert;
import com.finn.sys.quartz.entity.ScheduleJobLogEntity;
import com.finn.sys.quartz.mapper.ScheduleJobMapper;
import com.finn.sys.quartz.query.ScheduleJobLogQuery;
import com.finn.sys.quartz.service.ScheduleJobLogService;
import com.finn.sys.quartz.vo.ScheduleJobLogVO;
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