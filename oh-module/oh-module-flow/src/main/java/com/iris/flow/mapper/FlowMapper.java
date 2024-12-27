package com.iris.flow.mapper;

import com.github.pagehelper.Page;
import com.iris.flow.entity.FlowEntity;
import com.iris.flow.query.FlowQuery;
import com.iris.framework.datasource.annotations.Pages;
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
public interface FlowMapper {

    boolean updateById(@Param("param") FlowEntity flowEntity);

    FlowEntity getById(@Param("id")Long id);

    int save(FlowEntity flowEntity);

    @Pages
    Page<FlowEntity> getList(FlowQuery query);

    List<FlowEntity> getByKey(@Param("key")String key);
}