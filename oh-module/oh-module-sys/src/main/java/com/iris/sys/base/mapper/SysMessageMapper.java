package com.iris.sys.base.mapper;

import com.iris.framework.datasource.annotations.Pages;
import com.iris.sys.base.entity.SysMessageEntity;
import com.iris.sys.base.query.SysMessageQuery;
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
public interface SysMessageMapper {

    @Pages
    List<SysMessageEntity> getList(SysMessageQuery query);

    int save(SysMessageEntity param);

    boolean updateById(SysMessageEntity param);

    List<SysMessageEntity> getUnSendMsg(@Param("userId")Long userId, @Param("state")String state);

    SysMessageEntity getById(@Param("id")Long id);

}