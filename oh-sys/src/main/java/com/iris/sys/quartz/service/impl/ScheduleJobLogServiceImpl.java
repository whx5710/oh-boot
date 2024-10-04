package com.iris.sys.quartz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.framework.common.utils.PageResult;
import com.iris.sys.quartz.convert.ScheduleJobLogConvert;
import com.iris.sys.quartz.mapper.ScheduleJobMapper;
import com.iris.sys.quartz.entity.ScheduleJobLogEntity;
import com.iris.sys.quartz.query.ScheduleJobLogQuery;
import com.iris.sys.quartz.service.ScheduleJobLogService;
import com.iris.sys.quartz.vo.ScheduleJobLogVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<ScheduleJobLogEntity> list = scheduleJobMapper.getLogList(query);
        PageInfo<ScheduleJobLogEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(ScheduleJobLogConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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