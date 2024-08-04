package com.iris.system.quartz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.framework.common.utils.PageResult;
import com.iris.system.quartz.convert.ScheduleJobLogConvert;
import com.iris.system.quartz.dao.ScheduleJobDao;
import com.iris.system.quartz.entity.ScheduleJobLogEntity;
import com.iris.system.quartz.query.ScheduleJobLogQuery;
import com.iris.system.quartz.service.ScheduleJobLogService;
import com.iris.system.quartz.vo.ScheduleJobLogVO;
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

    private final ScheduleJobDao scheduleJobDao;

    public ScheduleJobLogServiceImpl(ScheduleJobDao scheduleJobDao){
        this.scheduleJobDao = scheduleJobDao;
    }
    @Override
    public PageResult<ScheduleJobLogVO> page(ScheduleJobLogQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<ScheduleJobLogEntity> list = scheduleJobDao.getLogList(query);
        PageInfo<ScheduleJobLogEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(ScheduleJobLogConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        scheduleJobDao.deleteLogByIds(idList);
    }

    @Override
    public int save(ScheduleJobLogEntity entity) {
        return scheduleJobDao.insertJobLog(entity);
    }

    @Override
    public ScheduleJobLogEntity getById(Long id) {
        return scheduleJobDao.getLogById(id);
    }

}