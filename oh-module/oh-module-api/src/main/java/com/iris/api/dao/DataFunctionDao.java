package com.iris.api.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iris.api.entity.DataFunctionEntity;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 功能列表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-30
*/
@Mapper
@DS(Constant.SYS_DB)
public interface DataFunctionDao extends BaseDao<DataFunctionEntity> {

    IPage<DataFunctionEntity> pageByClientId(IPage<DataFunctionEntity> iPage, @Param("clientId")String clientId);
}