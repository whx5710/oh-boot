package com.finn.sys.quartz.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.sys.quartz.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 定时任务日志
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface ScheduleJobLogMapper extends BaseMapper<ScheduleJobLogEntity> {
	
}