package com.iris.system.pim.dao;

import com.iris.framework.datasource.dao.BaseDao;
import com.iris.system.pim.entity.SysVersionInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 版本信息
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-09-16
*/
@Mapper
public interface SysVersionInfoDao extends BaseDao<SysVersionInfoEntity> {
	
}