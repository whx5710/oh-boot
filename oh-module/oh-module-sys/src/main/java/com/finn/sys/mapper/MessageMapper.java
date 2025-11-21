package com.finn.sys.mapper;

import com.finn.framework.datasource.annotations.Pages;
import com.finn.sys.entity.MessageEntity;
import com.finn.sys.query.MessageQuery;
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
public interface MessageMapper {

    @Pages
    List<MessageEntity> getList(MessageQuery query);

    int save(MessageEntity param);

    boolean updateById(MessageEntity param);

    List<MessageEntity> getUnSendMsg(@Param("userId")Long userId, @Param("state")String state);

    MessageEntity getById(@Param("id")Long id);

}