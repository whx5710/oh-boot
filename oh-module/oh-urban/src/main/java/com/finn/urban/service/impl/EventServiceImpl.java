package com.finn.urban.service.impl;

import com.finn.framework.entity.PageResult;
import com.finn.urban.convert.EventConvert;
import com.finn.urban.entity.Event;
import com.finn.urban.mapper.EventMapper;
import com.finn.urban.query.EventQuery;
import com.finn.urban.service.EventService;
import com.finn.urban.vo.EventVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 事件表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-06-14 17:42:45
 *
 */
@Service
public class EventServiceImpl implements EventService {

    private final EventMapper eventMapper;
    public EventServiceImpl(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    @Override
    public PageResult<EventVO> page(EventQuery query) {
        List<Event> list = eventMapper.getList(query);
        return new PageResult<>(EventConvert.INSTANCE.convertList(list), query.getTotal());
    }

    @Override
    public Long save(EventVO vo) {
        Event entity = EventConvert.INSTANCE.convert(vo);

        eventMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void update(EventVO vo) {
        Event entity = EventConvert.INSTANCE.convert(vo);
        eventMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            Event param = new Event();
            param.setId(id);
            param.setDbStatus(0);
            eventMapper.updateById(param);
        });
    }

}
