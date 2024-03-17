package com.iris.flow.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;
import com.iris.flow.entity.FlowNodeEntity;
import com.iris.flow.query.FlowNodeQuery;
import com.iris.flow.vo.FlowNodeVO;

import java.util.List;

/**
 * 环节定义表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-01-31
 */
public interface FlowNodeService extends BaseService<FlowNodeEntity> {

    PageResult<FlowNodeVO> page(FlowNodeQuery query);

    void save(FlowNodeVO vo);

    void update(FlowNodeVO vo);

    void delete(List<Long> idList);
}