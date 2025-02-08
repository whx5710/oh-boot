package com.finn.flow.service;

import com.finn.flow.entity.FlowEntity;
import com.finn.flow.query.FlowQuery;
import com.finn.flow.vo.FlowVO;
import com.finn.core.utils.PageResult;

import java.util.List;

/**
 * 自定义流程表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-12-19
 */
public interface FlowService {

    PageResult<FlowVO> page(FlowQuery query);

    void save(FlowVO vo);

    void update(FlowVO vo);

    void delete(List<Long> idList);

    FlowEntity getByKey(String key);

    FlowEntity getById(Long id);
}