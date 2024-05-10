package com.iris.system.quartz.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.datasource.dao.BaseDao;
import com.iris.system.quartz.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 定时任务
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
@DS(Constant.SYS_DB) // 初始化需缓存
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {
	
}