package com.iris.quartz.dao;

import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.quartz.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 定时任务日志
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {
	
}