package com.finn.team.service;

import com.finn.core.utils.PageResult;
import com.finn.team.entity.OhTaskUserEntity;
import com.finn.team.query.OhTaskUserQuery;
import com.finn.team.vo.OhTaskUserVO;

import java.util.List;

/**
 * 任务人员表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
public interface OhTaskUserService {

    PageResult<OhTaskUserVO> page(OhTaskUserQuery query);

    void save(OhTaskUserVO vo);

    void update(OhTaskUserVO vo);

    void delete(List<Long> idList);

    OhTaskUserEntity getById(Long id);
}