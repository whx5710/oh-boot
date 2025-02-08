package com.finn.flow.mapper;

import com.github.pagehelper.Page;
import com.finn.flow.entity.FlowNodeEntity;
import com.finn.flow.query.FlowNodeQuery;
import com.finn.framework.datasource.annotations.Pages;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 环节定义表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-01-31
*/
@Mapper
public interface FlowNodeMapper {

    @Pages
    Page<FlowNodeEntity> getList(FlowNodeQuery query);

    int save(FlowNodeEntity param);

    boolean updateById(FlowNodeEntity param);

    FlowNodeEntity getById(@Param("id")Long id);
}