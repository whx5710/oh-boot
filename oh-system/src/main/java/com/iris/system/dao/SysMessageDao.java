package com.iris.system.dao;

import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.system.entity.SysMessageEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 系统消息
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-10-10
*/
@Mapper
public interface SysMessageDao extends BaseDao<SysMessageEntity> {
	
}