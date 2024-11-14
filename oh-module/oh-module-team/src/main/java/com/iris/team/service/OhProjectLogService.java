package com.iris.team.service;

import com.iris.core.utils.PageResult;
import com.iris.team.entity.OhProjectLogEntity;
import com.iris.team.query.OhProjectLogQuery;
import com.iris.team.vo.OhProjectLogVO;

import java.util.List;

/**
 * 项目、任务操作日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
public interface OhProjectLogService {

    PageResult<OhProjectLogVO> page(OhProjectLogQuery query);

    void save(OhProjectLogVO vo);

    void update(OhProjectLogVO vo);

    void delete(List<Long> idList);

    OhProjectLogEntity getById(Long id);
}