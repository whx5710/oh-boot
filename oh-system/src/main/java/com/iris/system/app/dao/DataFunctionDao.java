package com.iris.system.app.dao;

import com.iris.system.app.entity.DataFunctionAuthorityEntity;
import com.iris.system.app.entity.DataFunctionEntity;
import com.iris.system.app.query.DataFunctionAuthorityQuery;
import com.iris.system.app.query.DataFunctionQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 功能列表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-30
*/
@Mapper
public interface DataFunctionDao {

    List<DataFunctionEntity> getList(DataFunctionQuery query);

    List<DataFunctionEntity> pageByClientId(@Param("clientId")String clientId);

    boolean updateById(DataFunctionEntity param);

    int insertFunc(DataFunctionEntity param);

    DataFunctionEntity getById(@Param("id")Long id);

    List<DataFunctionAuthorityEntity> getAuthorityList(DataFunctionAuthorityQuery params);

    int insertFuncAuthority(DataFunctionAuthorityEntity params);

    boolean updateFuncAuthorityById(DataFunctionAuthorityEntity params);

    boolean updateFuncAuthorityStatus(@Param("clientId") String clientId,@Param("funcCode") String funcCode,@Param("dbStatus") int dbStatus);

    DataFunctionAuthorityEntity getFuncAuthorityById(@Param("id")Long id);
}