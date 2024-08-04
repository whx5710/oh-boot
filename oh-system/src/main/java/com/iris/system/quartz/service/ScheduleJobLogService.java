package com.iris.system.quartz.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.system.quartz.entity.ScheduleJobLogEntity;
import com.iris.system.quartz.query.ScheduleJobLogQuery;
import com.iris.system.quartz.vo.ScheduleJobLogVO;

import java.util.List;

/**
 * 定时任务日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface ScheduleJobLogService {

    PageResult<ScheduleJobLogVO> page(ScheduleJobLogQuery query);

    void delete(List<Long> idList);

    int save(ScheduleJobLogEntity entity);

    ScheduleJobLogEntity getById(Long id);
}