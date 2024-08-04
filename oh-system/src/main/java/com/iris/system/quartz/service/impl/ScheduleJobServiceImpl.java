package com.iris.system.quartz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.framework.common.utils.PageResult;
import com.iris.system.quartz.convert.ScheduleJobConvert;
import com.iris.system.quartz.dao.ScheduleJobDao;
import com.iris.system.quartz.entity.ScheduleJobEntity;
import com.iris.system.quartz.enums.ScheduleStatusEnum;
import com.iris.system.quartz.query.ScheduleJobQuery;
import com.iris.system.quartz.service.ScheduleJobService;
import com.iris.system.quartz.utils.ScheduleUtils;
import com.iris.system.quartz.vo.ScheduleJobVO;
import jakarta.annotation.PostConstruct;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final ScheduleJobDao scheduleJobDao;

    public ScheduleJobServiceImpl(Scheduler scheduler, ScheduleJobDao scheduleJobDao) {
        this.scheduler = scheduler;
        this.scheduleJobDao = scheduleJobDao;
    }

    /**
     * 启动项目时，初始化定时器
     */
    @PostConstruct
    public void init() throws SchedulerException {
        scheduler.clear();
        List<ScheduleJobEntity> scheduleJobList = scheduleJobDao.getList(null);
        for (ScheduleJobEntity scheduleJob : scheduleJobList) {
            ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        }
    }

    @Override
    public PageResult<ScheduleJobVO> page(ScheduleJobQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<ScheduleJobEntity> list = scheduleJobDao.getList(query);
        PageInfo<ScheduleJobEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(ScheduleJobConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public void save(ScheduleJobVO vo) {
        ScheduleJobEntity entity = ScheduleJobConvert.INSTANCE.convert(vo);

        entity.setStatus(ScheduleStatusEnum.PAUSE.getValue());
        if (scheduleJobDao.insertJob(entity) > 0) {
            ScheduleUtils.createScheduleJob(scheduler, entity);
        }
    }

    @Override
    public void update(ScheduleJobVO vo) {
        ScheduleJobEntity entity = ScheduleJobConvert.INSTANCE.convert(vo);

        // 更新定时任务
        if (scheduleJobDao.updateById(entity)) {
            ScheduleJobEntity scheduleJob = scheduleJobDao.getById(entity.getId());
            ScheduleUtils.updateSchedulerJob(scheduler, scheduleJob);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            ScheduleJobEntity scheduleJob = scheduleJobDao.getById(id);
            // 删除定时任务
            ScheduleJobEntity param = new ScheduleJobEntity();
            param.setId(id);
            param.setDbStatus(0);
            if (scheduleJobDao.updateById(param)) {
                ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Override
    public void run(ScheduleJobVO vo) {
        ScheduleJobEntity scheduleJob = scheduleJobDao.getById(vo.getId());
        if (scheduleJob == null) {
            return;
        }

        ScheduleUtils.run(scheduler, scheduleJob);
    }

    @Override
    public void changeStatus(ScheduleJobVO vo) {
        ScheduleJobEntity scheduleJob = scheduleJobDao.getById(vo.getId());
        if (scheduleJob == null) {
            return;
        }

        // 更新数据
        scheduleJob.setStatus(vo.getStatus());
        scheduleJobDao.updateById(scheduleJob);

        if (ScheduleStatusEnum.PAUSE.getValue() == vo.getStatus()) {
            ScheduleUtils.pauseJob(scheduler, scheduleJob);
        } else if (ScheduleStatusEnum.NORMAL.getValue() == vo.getStatus()) {
            ScheduleUtils.resumeJob(scheduler, scheduleJob);
        }
    }

    @Override
    public ScheduleJobEntity getById(Long id) {
        return scheduleJobDao.getById(id);
    }

}