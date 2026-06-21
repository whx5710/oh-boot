package com.finn.urban.service.impl;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.framework.exception.ServerException;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import com.finn.framework.utils.AssertUtils;
import com.finn.urban.convert.EventConvert;
import com.finn.urban.convert.MultiMediaConvert;
import com.finn.urban.entity.Event;
import com.finn.urban.entity.MultiMedia;
import com.finn.urban.mapper.EventMapper;
import com.finn.urban.mapper.MultiMediaMapper;
import com.finn.urban.query.EventQuery;
import com.finn.urban.service.EventService;
import com.finn.urban.vo.EventVO;
import com.finn.urban.vo.MultiMediaVO;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.finn.framework.common.constant.Constant.DESC;

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
    public Page<Event> page(EventQuery query) {
        QueryWrapper<Event> queryWrapper = getQueryWrapper(query);
        queryWrapper.page(query.getPageNum(), query.getPageSize());
        return eventMapper.listByWrapper(queryWrapper);
    }

    @Override
    public Long save(EventVO vo) {
        UserDetail user = SecurityUser.getUser();
        if(user == null){
            throw new ServerException("未找到用户信息");
        }
        if(user.getOpenId() == null){
            throw new ServerException("用户ID不能为空");
        }
        Event entity = EventConvert.INSTANCE.convert(vo);
        entity.setCode(redisCache.getDayIncrementCode("","urban:event:code", 5));
        if(entity.getReportTime() == null){
            entity.setReportTime(LocalDateTime.now());
        }
        entity.setAcceptStatus("1");
        entity.setOpenId(user.getOpenId());
        eventMapper.insert(entity);

        // 保存附件
        if(vo.getMediaList() != null && !vo.getMediaList().isEmpty()){
            for(MultiMediaVO item: vo.getMediaList()){
                if(item.getFileId() != null){
                    MultiMedia media = MultiMediaConvert.INSTANCE.convert(item);
                    media.setStatusType("1");
                    media.setEvtId(entity.getId());
                    if(item.getFileName() == null){
                        media.setFileName(item.getFileId());
                    }
                    media.setDbStatus(1);
                    multiMediaMapper.insert(media);
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

    @Override
    public Page<Event> myEvent(EventQuery query) {
        UserDetail user = SecurityUser.getUser();
        if(user == null){
            throw new ServerException("未找到用户信息");
        }
        if(user.getOpenId() == null){
            throw new ServerException("用户ID不能为空");
        }
        QueryWrapper<Event> queryWrapper = getQueryWrapper(query);
        queryWrapper.eq(Event::getOpenId, user.getOpenId())
                .page(query.getPageNum(), query.getPageSize());
        return eventMapper.listByWrapper(queryWrapper);
    }

    @Override
    public EventVO detail(Long id) {
        AssertUtils.isNull(id, "ID");
        Event event = eventMapper.findById(id, Event.class);
        if(event == null || event.getId() == null){
            throw new ServerException("未找到事件信息");
        }
        EventVO eventVO = EventConvert.INSTANCE.convert(event);

        QueryWrapper<MultiMedia> queryWrapper = QueryWrapper.of(MultiMedia.class);
        queryWrapper.eq(MultiMedia::getDbStatus, 1)
                        .eq(MultiMedia::getEvtId, event.getId())
                .orderBy(MultiMedia::getCreateTime);
        List<MultiMedia> mediaList = multiMediaMapper.listByWrapper(queryWrapper);
        if(mediaList != null && !mediaList.isEmpty()){
            eventVO.setMediaList(MultiMediaConvert.INSTANCE.convertList(mediaList));
        }
        return eventVO;
    }

    /**
     * 组装查询条件
     * @param query
     * @return
     */
    private QueryWrapper<Event> getQueryWrapper(EventQuery query){
        if(query.getAcceptStatus() != null && query.getAcceptStatus().isEmpty()){
            query.setAcceptStatus(null);
        }
        QueryWrapper<Event> queryWrapper = QueryWrapper.of(Event.class);
        queryWrapper.eq(Event::getAcceptStatus, query.getAcceptStatus())
                .eq(Event::getAcceptStatus, query.getAnonymous())
                .eq(Event::getDbStatus, 1)
                .eq(Event::getMobile, query.getMobile())
                .eq(Event::getCode, query.getCode())
                .ge(Event::getReportTime, query.getReportStartTime())
                .le(Event::getReportTime, query.getReportEndTime())
                .orderBy(Event::getReportTime, DESC);
        if(query.getKeyWord() != null && !query.getKeyWord().isEmpty()){
            queryWrapper.jointSQL("(code like concat('%',#{keyWord}, '%') or description like concat('%', #{keyWord},'%') or location like concat('%', #{keyWord},'%'))",
                    "keyWord", query.getKeyWord());
        }
        return queryWrapper;
    }

}
