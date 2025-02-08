package com.finn.flow.mapper;

import com.github.pagehelper.Page;
import com.finn.flow.entity.WorkOrderEntity;
import com.finn.flow.query.WorkOrderQuery;
import com.finn.framework.datasource.annotations.Pages;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
* 工单表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-02-23
*/
@Mapper
public interface WorkOrderMapper {

    @Pages
    Page<WorkOrderEntity> getOrderList(WorkOrderQuery query);

    int saveOrder(WorkOrderEntity param);

    boolean updateOrderById(WorkOrderEntity param);

    WorkOrderEntity getOrderById(@Param("id")Long id);
}