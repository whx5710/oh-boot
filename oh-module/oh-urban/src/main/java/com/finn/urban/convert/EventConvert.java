package com.finn.urban.convert;

import com.finn.urban.entity.Event;
import com.finn.urban.vo.EventVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 事件表
 * @since 1.0.0 2026-06-14 17:42:45
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface EventConvert {

    EventConvert INSTANCE = Mappers.getMapper(EventConvert.class);

    Event convert(EventVO vo);

    EventVO convert(Event entity);

    List<EventVO> convertList(List<Event> list);

}
