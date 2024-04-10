package com.iris.team.dao;

import com.iris.framework.datasource.dao.BaseDao;
import com.iris.team.entity.OhProjectLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 项目、任务操作日志
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Mapper
public interface OhProjectLogDao extends BaseDao<OhProjectLogEntity> {
	
}