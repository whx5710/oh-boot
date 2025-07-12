package com.finn.team.service;

import com.finn.core.utils.PageResult;
import com.finn.team.entity.OhProjectEntity;
import com.finn.team.query.OhProjectQuery;
import com.finn.team.vo.OhProjectVO;

import java.util.List;

/**
 * 项目信息表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
public interface OhProjectService {

    PageResult<OhProjectVO> page(OhProjectQuery query);

    void save(OhProjectVO vo);

    void update(OhProjectVO vo);

    void delete(List<Long> idList);

    OhProjectEntity getById(Long id);
}