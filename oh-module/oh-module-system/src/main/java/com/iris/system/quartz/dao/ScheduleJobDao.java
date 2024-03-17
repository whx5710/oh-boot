package com.iris.system.quartz.dao;

import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.system.quartz.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 定时任务
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {
	
}