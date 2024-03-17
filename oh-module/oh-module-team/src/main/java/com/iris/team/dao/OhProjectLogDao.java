package com.iris.team.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.team.entity.OhProjectLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 项目、任务操作日志
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Mapper
@DS(Constant.PROJECT_DB)
public interface OhProjectLogDao extends BaseDao<OhProjectLogEntity> {
	
}