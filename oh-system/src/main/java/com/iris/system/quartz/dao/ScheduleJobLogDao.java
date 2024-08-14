package com.iris.system.quartz.dao;

import com.iris.framework.common.constant.Constant;
import com.iris.framework.datasource.annotations.Ds;
import com.iris.framework.datasource.dao.BaseDao;
import com.iris.system.quartz.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 定时任务日志
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
@Ds(Constant.SYS_DB)
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {
	
}