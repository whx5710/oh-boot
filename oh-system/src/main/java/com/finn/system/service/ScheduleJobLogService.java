package com.finn.system.service;

import com.finn.core.utils.PageResult;
import com.finn.system.entity.ScheduleJobLogEntity;
import com.finn.system.query.ScheduleJobLogQuery;
import com.finn.system.vo.ScheduleJobLogVO;

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