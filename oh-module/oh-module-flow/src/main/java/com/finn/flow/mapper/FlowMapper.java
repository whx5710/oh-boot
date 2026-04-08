package com.finn.flow.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.flow.entity.FlowEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 自定义流程表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-12-19
*/
@Mapper
public interface FlowMapper extends BaseMapper<FlowEntity> {

    FlowEntity getById(@Param("id")Long id);

    List<FlowEntity> getByKey(@Param("key")String key);
}