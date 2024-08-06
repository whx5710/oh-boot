package com.iris.team.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.team.entity.OhProjectEntity;
import com.iris.team.vo.OhProjectVO;
import com.iris.team.query.OhProjectQuery;

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