package com.iris.system.dao;

import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.system.entity.SmsLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 短信日志
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface SmsLogDao extends BaseDao<SmsLogEntity> {
	
}