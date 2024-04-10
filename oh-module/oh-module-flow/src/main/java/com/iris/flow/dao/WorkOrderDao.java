package com.iris.flow.dao;

import com.iris.framework.datasource.dao.BaseDao;
import com.iris.flow.entity.WorkOrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 工单表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-02-23
*/
@Mapper
public interface WorkOrderDao extends BaseDao<WorkOrderEntity> {
	
}