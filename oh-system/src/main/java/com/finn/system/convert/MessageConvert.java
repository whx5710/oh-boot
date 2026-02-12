package com.finn.system.convert;

import com.finn.system.entity.MessageEntity;
import com.finn.system.vo.MessageVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 系统消息
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-10-10
*/
@Mapper
public interface MessageConvert {
    MessageConvert INSTANCE = Mappers.getMapper(MessageConvert.class);

    MessageEntity convert(MessageVO vo);

    MessageVO convert(MessageEntity entity);

    List<MessageVO> convertList(List<MessageEntity> list);

}