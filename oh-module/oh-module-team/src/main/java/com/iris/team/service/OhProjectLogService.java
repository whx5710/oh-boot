package com.iris.team.service;

import com.iris.team.vo.OhProjectLogVO;
import com.iris.team.entity.OhProjectLogEntity;
import com.iris.team.query.OhProjectLogQuery;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;

import java.util.List;

/**
 * 项目、任务操作日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
public interface OhProjectLogService extends BaseService<OhProjectLogEntity> {

    PageResult<OhProjectLogVO> page(OhProjectLogQuery query);

    void save(OhProjectLogVO vo);

    void update(OhProjectLogVO vo);

    void delete(List<Long> idList);
}