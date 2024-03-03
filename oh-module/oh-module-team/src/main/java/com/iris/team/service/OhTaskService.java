package com.iris.team.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.team.vo.OhTaskVO;
import com.iris.team.entity.OhTaskEntity;
import com.iris.team.query.OhTaskQuery;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;

import java.util.List;

/**
 * 任务表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
@DS("businessDB")
public interface OhTaskService extends BaseService<OhTaskEntity> {

    PageResult<OhTaskVO> page(OhTaskQuery query);

    void save(OhTaskVO vo);

    void update(OhTaskVO vo);

    void delete(List<Long> idList);
}