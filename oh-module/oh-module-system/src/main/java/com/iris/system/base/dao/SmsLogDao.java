package com.iris.system.base.dao;

import com.iris.framework.datasource.dao.BaseDao;
import com.iris.system.base.entity.SmsLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 短信日志
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface SmsLogDao extends BaseDao<SmsLogEntity> {
	
}