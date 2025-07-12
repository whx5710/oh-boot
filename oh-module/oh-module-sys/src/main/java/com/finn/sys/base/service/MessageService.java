package com.finn.sys.base.service;

import com.finn.core.utils.PageResult;
import com.finn.sys.base.entity.MessageEntity;
import com.finn.sys.base.query.MessageQuery;
import com.finn.sys.base.vo.MessageVO;

import java.util.List;

/**
 * 系统消息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-10-10
 */
public interface MessageService {

    PageResult<MessageVO> page(MessageQuery query);

    void save(MessageVO vo);

    void update(MessageVO vo);

    void delete(List<Long> idList);

    List<MessageVO> unSendMsg(Long userId);

    List<MessageVO> unReadMsg(Long userId);

    MessageEntity getById(Long id);

    boolean updateById(MessageEntity param);
}