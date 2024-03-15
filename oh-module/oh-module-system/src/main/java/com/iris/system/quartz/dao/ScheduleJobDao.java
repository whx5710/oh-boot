package com.iris.system.quartz.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.system.quartz.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 定时任务
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
@DS(Constant.SYS_DB)
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {
	
}