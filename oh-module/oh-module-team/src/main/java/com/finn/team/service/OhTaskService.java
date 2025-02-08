package com.finn.team.service;

import com.finn.core.utils.PageResult;
import com.finn.team.entity.OhTaskEntity;
import com.finn.team.query.OhTaskQuery;
import com.finn.team.vo.OhTaskVO;

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

    OhTaskEntity getById(Long id);
}