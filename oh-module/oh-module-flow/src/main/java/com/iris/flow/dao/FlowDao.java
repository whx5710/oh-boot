package com.iris.flow.dao;

import com.iris.framework.datasource.dao.BaseDao;
import com.iris.flow.entity.FlowEntity;
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