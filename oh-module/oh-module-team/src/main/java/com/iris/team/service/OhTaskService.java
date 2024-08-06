package com.iris.team.service;

import com.iris.team.vo.OhTaskVO;
import com.iris.team.query.OhTaskQuery;
import com.iris.framework.common.utils.PageResult;

import java.util.List;

/**
 * 任务表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
public interface OhTaskService {

    PageResult<OhTaskVO> page(OhTaskQuery query);

    void save(OhTaskVO vo);

    void update(OhTaskVO vo);

    void delete(List<Long> idList);
}