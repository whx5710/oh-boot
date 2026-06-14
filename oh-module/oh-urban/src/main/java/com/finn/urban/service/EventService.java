package com.finn.urban.service;

import com.finn.framework.entity.PageResult;
import com.finn.urban.query.EventQuery;
import com.finn.urban.vo.EventVO;

import java.util.List;

/**
 * 事件表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-06-14 17:42:45
 *
 */
public interface EventService {

    PageResult<EventVO> page(EventQuery query);

    Long save(EventVO vo);

    void update(EventVO vo);

    void delete(List<Long> idList);
}
