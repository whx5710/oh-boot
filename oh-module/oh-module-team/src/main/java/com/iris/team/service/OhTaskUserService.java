package com.iris.team.service;

import com.iris.team.vo.OhTaskUserVO;
import com.iris.team.entity.OhTaskUserEntity;
import com.iris.team.query.OhTaskUserQuery;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.BaseService;

import java.util.List;

/**
 * 任务人员表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
public interface OhTaskUserService extends BaseService<OhTaskUserEntity> {

    PageResult<OhTaskUserVO> page(OhTaskUserQuery query);

    void save(OhTaskUserVO vo);

    void update(OhTaskUserVO vo);

    void delete(List<Long> idList);
}