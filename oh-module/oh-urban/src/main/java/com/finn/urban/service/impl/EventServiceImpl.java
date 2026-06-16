package com.finn.urban.service.impl;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.framework.entity.PageResult;
import com.finn.urban.convert.EventConvert;
import com.finn.urban.entity.Event;
import com.finn.urban.entity.MultiMedia;
import com.finn.urban.mapper.EventMapper;
import com.finn.urban.mapper.MultiMediaMapper;
import com.finn.urban.query.EventQuery;
import com.finn.urban.service.EventService;
import com.finn.urban.vo.EventVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final RedisCache redisCache;
    private final MultiMediaMapper multiMediaMapper;
    public EventServiceImpl(EventMapper eventMapper, RedisCache redisCache,
                            MultiMediaMapper multiMediaMapper) {
        this.eventMapper = eventMapper;
        this.redisCache = redisCache;
        this.multiMediaMapper = multiMediaMapper;
    }

    @Override
    public PageResult<EventVO> page(EventQuery query) {
        List<Event> list = eventMapper.getList(query);
        return new PageResult<>(EventConvert.INSTANCE.convertList(list), query.getTotal());
    }

    @Override
    public Long save(EventVO vo) {
        Event entity = EventConvert.INSTANCE.convert(vo);
        entity.setCode(redisCache.getDayIncrementCode("","urban:event:code", 5));
        if(entity.getReportTime() == null){
            entity.setReportTime(LocalDateTime.now());
        }
        entity.setAcceptStatus("1");
        eventMapper.insert(entity);

        // 保存附件
        if(vo.getMediaList() != null && !vo.getMediaList().isEmpty()){
            for(MultiMedia item: vo.getMediaList()){
                if(item.getFileId() != null){
                    item.setStatusType("1");
                    if(item.getFileName() == null){
                        item.setFileName(item.getFileId());
                    }
                    item.setDbStatus(1);
                    multiMediaMapper.insert(item);
                }
            }
        }
        return entity.getId();
    }

    @Override
    public void update(EventVO vo) {
        Event entity = EventConvert.INSTANCE.convert(vo);
        eventMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        UpdateWrapper<Event> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(Event::getDbStatus, 0).in(Event::getId, idList);
        eventMapper.updateByWrapper(updateWrapper);
    }

}
