package com.iris.flow.service;

import com.iris.flow.entity.FlowEntity;
import com.iris.flow.query.FlowQuery;
import com.iris.flow.vo.FlowVO;
import com.iris.core.utils.PageResult;

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