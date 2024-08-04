package com.iris.flow.dao;

import com.iris.flow.entity.FlowNodeEntity;
import com.iris.flow.query.FlowNodeQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 环节定义表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-01-31
*/
@Mapper
public interface FlowNodeDao {

    List<FlowNodeEntity> getList(FlowNodeQuery query);

    int save(FlowNodeEntity param);

    boolean updateById(FlowNodeEntity param);

    FlowNodeEntity getById(@Param("id")Long id);
}