package com.iris.api.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.api.entity.DataFunctionAuthorityEntity;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
* 客户端接口授权
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-29
*/
@Mapper
@DS(Constant.SYS_DB)
public interface DataFunctionAuthorityDao extends BaseDao<DataFunctionAuthorityEntity> {
	
}