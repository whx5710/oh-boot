package com.iris.sys.quartz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.core.utils.PageResult;
import com.iris.sys.quartz.convert.ScheduleJobConvert;
import com.iris.sys.quartz.entity.ScheduleJobEntity;
import com.iris.sys.quartz.enums.ScheduleStatusEnum;
import com.iris.sys.quartz.mapper.ScheduleJobMapper;
import com.iris.sys.quartz.query.ScheduleJobQuery;
import com.iris.sys.quartz.service.ScheduleJobService;
import com.iris.sys.quartz.utils.ScheduleUtils;
import com.iris.sys.quartz.vo.ScheduleJobVO;
import jakarta.annotation.PostConstruct;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
    private final Scheduler scheduler;

    private final ScheduleJobMapper scheduleJobMapper;

    public ScheduleJobServiceImpl(Scheduler scheduler, ScheduleJobMapper scheduleJobMapper) {
        this.scheduler = scheduler;
        this.scheduleJobMapper = scheduleJobMapper;
    }

    /**
     * 启动项目时，初始化定时器
     */
//    @PostConstruct
    public void init() throws SchedulerException {
        scheduler.clear();
        List<ScheduleJobEntity> scheduleJobList = scheduleJobMapper.getList(null);
        for (ScheduleJobEntity scheduleJob : scheduleJobList) {
            ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        }
    }

    @Override
    public PageResult<ScheduleJobVO> page(ScheduleJobQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<ScheduleJobEntity> list = scheduleJobMapper.getList(query);
        PageInfo<ScheduleJobEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(ScheduleJobConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public void save(ScheduleJobVO vo) {
        ScheduleJobEntity entity = ScheduleJobConvert.INSTANCE.convert(vo);

        entity.setStatus(ScheduleStatusEnum.PAUSE.getValue());
        if (scheduleJobMapper.insertJob(entity) > 0) {
            ScheduleUtils.createScheduleJob(scheduler, entity);
        }
    }

    @Override
    public void update(ScheduleJobVO vo) {
        ScheduleJobEntity entity = ScheduleJobConvert.INSTANCE.convert(vo);

        // 更新定时任务
        if (scheduleJobMapper.updateById(entity)) {
            ScheduleJobEntity scheduleJob = scheduleJobMapper.getById(entity.getId());
            ScheduleUtils.updateSchedulerJob(scheduler, scheduleJob);
        }
    }

    @Override
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            ScheduleJobEntity scheduleJob = scheduleJobMapper.getById(id);
            // 删除定时任务
            ScheduleJobEntity param = new ScheduleJobEntity();
            param.setId(id);
            param.setDbStatus(0);
            if (scheduleJobMapper.updateById(param)) {
                ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Override
    public void run(ScheduleJobVO vo) {
        ScheduleJobEntity scheduleJob = scheduleJobMapper.getById(vo.getId());
        if (scheduleJob == null) {
            return;
        }

        ScheduleUtils.run(scheduler, scheduleJob);
    }

    @Override
    public void changeStatus(ScheduleJobVO vo) {
        ScheduleJobEntity scheduleJob = scheduleJobMapper.getById(vo.getId());
        if (scheduleJob == null) {
            return;
        }

        // 更新数据
        scheduleJob.setStatus(vo.getStatus());
        scheduleJobMapper.updateById(scheduleJob);

        if (ScheduleStatusEnum.PAUSE.getValue() == vo.getStatus()) {
            ScheduleUtils.pauseJob(scheduler, scheduleJob);
        } else if (ScheduleStatusEnum.NORMAL.getValue() == vo.getStatus()) {
            ScheduleUtils.resumeJob(scheduler, scheduleJob);
        }
    }

    @Override
    public ScheduleJobEntity getById(Long id) {
        return scheduleJobMapper.getById(id);
    }

}