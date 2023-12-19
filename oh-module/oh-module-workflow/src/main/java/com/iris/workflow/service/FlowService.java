package com.iris.workflow.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;
import com.iris.workflow.entity.FlowEntity;
import com.iris.workflow.query.FlowQuery;
import com.iris.workflow.vo.FlowVO;

import java.util.List;

/**
 * 自定义流程表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-12-19
 */
public interface FlowService extends BaseService<FlowEntity> {

    PageResult<FlowVO> page(FlowQuery query);

    void save(FlowVO vo);

    void update(FlowVO vo);

    void delete(List<Long> idList);

    FlowVO getByKey(String key);
}