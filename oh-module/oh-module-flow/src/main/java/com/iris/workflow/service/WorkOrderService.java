package com.iris.workflow.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;
import com.iris.workflow.vo.WorkOrderVO;
import com.iris.workflow.query.WorkOrderQuery;
import com.iris.workflow.entity.WorkOrderEntity;

import java.util.List;

/**
 * 工单表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-23
 */
public interface WorkOrderService extends BaseService<WorkOrderEntity> {

    PageResult<WorkOrderVO> page(WorkOrderQuery query);

    void save(WorkOrderVO vo);

    void update(WorkOrderVO vo);

    void delete(List<Long> idList);
}