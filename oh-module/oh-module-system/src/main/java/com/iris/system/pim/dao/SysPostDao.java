package com.iris.system.pim.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.framework.common.constant.Constant;
import com.iris.system.pim.entity.SysPostEntity;
import com.iris.framework.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
* 岗位管理
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
@DS(Constant.SYS_DB)
public interface SysPostDao extends BaseDao<SysPostEntity> {
	
}