package com.finn.system.service.impl;

import com.finn.core.utils.PageResult;
import com.finn.framework.security.user.SecurityUser;
import com.finn.system.convert.MessageConvert;
import com.finn.system.entity.MessageEntity;
import com.finn.system.mapper.MessageMapper;
import com.finn.system.query.MessageQuery;
import com.finn.system.service.MessageService;
import com.finn.system.vo.MessageVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 系统消息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-10-10
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageMapper messageMapper){
        this.messageMapper = messageMapper;
    }

    @Override
    public PageResult<MessageVO> page(MessageQuery query) {
        List<MessageEntity> list = messageMapper.getList(query);
        return new PageResult<>(MessageConvert.INSTANCE.convertList(list), query.getTotal());
    }

    @Override
    public void save(MessageVO vo) {
        MessageEntity entity = MessageConvert.INSTANCE.convert(vo);
        if(ObjectUtils.isEmpty(entity.getFromId())){
            entity.setFromId(SecurityUser.getUserId());
            entity.setFromName(SecurityUser.getUser().getRealName());
        }
        entity.setType("success");
        messageMapper.save(entity);
    }

    @Override
    public void update(MessageVO vo) {
        MessageEntity entity = MessageConvert.INSTANCE.convert(vo);
        messageMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            MessageEntity param = new MessageEntity();
            param.setId(id);
            param.setDbStatus(0);
            messageMapper.updateById(param);
        });
    }

    @Override
    public List<MessageVO> unSendMsg(Long userId) {
        List<MessageEntity> list = messageMapper.getUnSendMsg(userId, "0");
        if(list.size() > 10){
            return MessageConvert.INSTANCE.convertList(list.subList(0, 10));
        }
        return MessageConvert.INSTANCE.convertList(list);
    }

    /**
     * 获取未读消息
     * @param userId
     * @return
     */
    @Override
    public List<MessageVO> unReadMsg(Long userId) {
        List<MessageEntity> list = messageMapper.getUnSendMsg(userId, "1");
        if(list.size() > 10){
            return MessageConvert.INSTANCE.convertList(list.subList(0, 10));
        }
        return MessageConvert.INSTANCE.convertList(list);
    }

    @Override
    public MessageEntity getById(Long id) {
        return messageMapper.getById(id);
    }

    @Override
    public boolean updateById(MessageEntity param) {
        return messageMapper.updateById(param);
    }

}