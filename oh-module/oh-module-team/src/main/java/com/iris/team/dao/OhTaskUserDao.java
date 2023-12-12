package com.iris.team.dao;

import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.team.entity.OhTaskUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 任务人员表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Mapper
public interface OhTaskUserDao extends BaseDao<OhTaskUserEntity> {
	
}