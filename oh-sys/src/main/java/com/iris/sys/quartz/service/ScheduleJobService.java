package com.iris.sys.quartz.service;

import com.iris.core.utils.PageResult;
import com.iris.sys.quartz.entity.ScheduleJobEntity;
import com.iris.sys.quartz.query.ScheduleJobQuery;
import com.iris.sys.quartz.vo.ScheduleJobVO;

import java.util.List;

/**
 * 定时任务
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface ScheduleJobService {

    PageResult<ScheduleJobVO> page(ScheduleJobQuery query);

    void save(ScheduleJobVO vo);

    void update(ScheduleJobVO vo);

    void delete(List<Long> idList);

    void run(ScheduleJobVO vo);

    void changeStatus(ScheduleJobVO vo);

    ScheduleJobEntity getById(Long id);
}