package com.finn.flow.service;

import com.finn.flow.entity.FlowNodeEntity;
import com.finn.flow.query.FlowNodeQuery;
import com.finn.flow.vo.FlowNodeVO;
import com.finn.core.utils.PageResult;

import java.util.List;

/**
 * 环节定义表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-01-31
 */
public interface FlowNodeService {

    PageResult<FlowNodeVO> page(FlowNodeQuery query);

    void save(FlowNodeVO vo);

    void update(FlowNodeVO vo);

    void delete(List<Long> idList);

    FlowNodeEntity getById(Long id);
}