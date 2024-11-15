package com.iris.sys.quartz.mapper;

import com.iris.framework.datasource.mapper.BaseMapper;
import com.iris.sys.quartz.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 定时任务日志
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface ScheduleJobLogMapper extends BaseMapper<ScheduleJobLogEntity> {
	
}