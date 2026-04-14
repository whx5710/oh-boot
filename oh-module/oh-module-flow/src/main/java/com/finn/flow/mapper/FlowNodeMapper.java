package com.finn.flow.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.flow.entity.FlowNodeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 环节定义表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-01-31
*/
@Mapper
public interface FlowNodeMapper extends BaseMapper<FlowNodeEntity> {

    FlowNodeEntity getById(@Param("id")Long id);
}