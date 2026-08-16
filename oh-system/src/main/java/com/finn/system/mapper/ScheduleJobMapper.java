package com.finn.system.mapper;

import com.finn.system.entity.ScheduleJobEntity;
import com.finn.system.entity.ScheduleJobLogEntity;
import com.finn.system.query.ScheduleJobLogQuery;
import com.finn.system.query.ScheduleJobQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 定时任务
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface ScheduleJobMapper {

    List<ScheduleJobLogEntity> getLogList(ScheduleJobLogQuery query);

    int deleteLogByIds(@Param("ids")List<Long> idList);

    int insertJobLog(ScheduleJobLogEntity entity);
    List<ScheduleJobEntity> getList(ScheduleJobQuery query);

    boolean updateById(ScheduleJobEntity param);

    int insertJob(ScheduleJobEntity param);

    ScheduleJobEntity getById(@Param("id")Long id);

    ScheduleJobLogEntity getLogById(@Param("id")Long id);

}