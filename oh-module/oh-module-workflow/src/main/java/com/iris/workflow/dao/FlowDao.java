package com.iris.workflow.dao;

import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.workflow.entity.FlowEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 自定义流程表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-12-19
*/
@Mapper
public interface FlowDao extends BaseDao<FlowEntity> {
	
}