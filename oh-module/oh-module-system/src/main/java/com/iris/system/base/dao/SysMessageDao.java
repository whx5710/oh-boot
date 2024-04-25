package com.iris.system.base.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.datasource.dao.BaseDao;
import com.iris.system.base.entity.SysMessageEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 系统消息
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-10-10
*/
@Mapper
@DS(Constant.SYS_DB)
public interface SysMessageDao extends BaseDao<SysMessageEntity> {
	
}