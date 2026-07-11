package com.finn.urban.service;

import com.finn.urban.entity.Event;
import com.finn.urban.query.EventQuery;
import com.finn.urban.vo.EventVO;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 事件表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-06-14 17:42:45
 *
 */
public interface EventService {

    Page<Event> page(EventQuery query);

    Long save(EventVO vo);

    void update(EventVO vo);

    void delete(List<Long> idList);

    Page<Event> myEvent(EventQuery query);

    EventVO detail(Long id);
}
