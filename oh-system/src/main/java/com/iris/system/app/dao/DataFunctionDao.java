package com.iris.system.app.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iris.framework.datasource.dao.BaseDao;
import com.iris.system.app.entity.DataFunctionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 功能列表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-30
*/
@Mapper
public interface DataFunctionDao extends BaseDao<DataFunctionEntity> {

    IPage<DataFunctionEntity> pageByClientId(IPage<DataFunctionEntity> iPage, @Param("clientId")String clientId);
}