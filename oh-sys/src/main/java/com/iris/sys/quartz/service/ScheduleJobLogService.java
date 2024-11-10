package com.iris.sys.quartz.service;

import com.iris.core.utils.PageResult;
import com.iris.sys.quartz.entity.ScheduleJobLogEntity;
import com.iris.sys.quartz.query.ScheduleJobLogQuery;
import com.iris.sys.quartz.vo.ScheduleJobLogVO;

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