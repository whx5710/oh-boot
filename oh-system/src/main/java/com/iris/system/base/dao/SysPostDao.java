package com.iris.system.base.dao;

import com.iris.system.base.entity.SysPostEntity;
import com.iris.framework.datasource.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
* 岗位管理
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface SysPostDao extends BaseDao<SysPostEntity> {
	
}