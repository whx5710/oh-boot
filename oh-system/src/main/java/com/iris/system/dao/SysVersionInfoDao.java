package com.iris.system.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.system.entity.SysVersionInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 版本信息
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-09-16
*/
@Mapper
@DS(Constant.SYS_DB)
public interface SysVersionInfoDao extends BaseDao<SysVersionInfoEntity> {
	
}