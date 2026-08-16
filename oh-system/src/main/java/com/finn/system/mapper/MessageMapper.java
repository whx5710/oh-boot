package com.finn.system.mapper;

import com.finn.framework.aop.annotations.Pages;
import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.system.entity.MessageEntity;
import com.finn.system.query.MessageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 系统消息
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-10-10
*/
@Mapper
public interface MessageMapper extends BaseMapper<MessageEntity> {

    @Pages
    List<MessageEntity> getList(MessageQuery query);

    List<MessageEntity> getUnSendMsg(@Param("userId")Long userId, @Param("state")String state);

}