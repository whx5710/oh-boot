package com.iris.api.dao;

import com.iris.api.entity.DataFunctionAuthorityEntity;
import com.iris.framework.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
* 客户端接口授权
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-29
*/
@Mapper
public interface DataFunctionAuthorityDao extends BaseDao<DataFunctionAuthorityEntity> {
	
}