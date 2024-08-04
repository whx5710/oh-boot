package com.iris.flow.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.flow.vo.WorkOrderVO;
import com.iris.flow.query.WorkOrderQuery;
import com.iris.flow.entity.WorkOrderEntity;

import java.util.List;

/**
 * 工单表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-23
 */
public interface WorkOrderService {

    PageResult<WorkOrderVO> page(WorkOrderQuery query);

    void save(WorkOrderVO vo);

    void update(WorkOrderVO vo);

    void delete(List<Long> idList);

    WorkOrderEntity getOrderById(Long id);
}