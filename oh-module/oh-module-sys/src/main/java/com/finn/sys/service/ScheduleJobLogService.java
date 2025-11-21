package com.finn.sys.service;

import com.finn.core.utils.PageResult;
import com.finn.sys.entity.ScheduleJobLogEntity;
import com.finn.sys.query.ScheduleJobLogQuery;
import com.finn.sys.vo.ScheduleJobLogVO;

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