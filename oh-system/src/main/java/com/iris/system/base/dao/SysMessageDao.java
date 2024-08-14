package com.iris.system.base.dao;

import com.iris.framework.common.constant.Constant;
import com.iris.framework.datasource.annotations.Ds;
import com.iris.system.base.entity.SysMessageEntity;
import com.iris.system.base.query.SysMessageQuery;
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
@Ds(Constant.SYS_DB)
public interface SysMessageDao {

    List<SysMessageEntity> getList(SysMessageQuery query);

    int save(SysMessageEntity param);

    boolean updateById(SysMessageEntity param);

    List<SysMessageEntity> getUnSendMsg(@Param("userId")Long userId, @Param("state")String state);

    SysMessageEntity getById(@Param("id")Long id);

}