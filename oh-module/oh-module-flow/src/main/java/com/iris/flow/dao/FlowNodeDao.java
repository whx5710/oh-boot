package com.iris.flow.dao;

import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.flow.entity.FlowNodeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 环节定义表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-01-31
*/
@Mapper
public interface FlowNodeDao extends BaseDao<FlowNodeEntity> {
	
}