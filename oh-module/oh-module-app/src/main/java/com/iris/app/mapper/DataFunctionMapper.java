package com.iris.app.mapper;

import com.iris.app.entity.DataFunctionAuthorityEntity;
import com.iris.app.entity.DataFunctionEntity;
import com.iris.app.query.DataFunctionAuthorityQuery;
import com.iris.app.query.DataFunctionQuery;
import com.iris.framework.datasource.annotations.Pages;
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
public interface DataFunctionMapper {

    List<DataFunctionEntity> getList(DataFunctionQuery query);

    List<DataFunctionEntity> pageByClientId(@Param("clientId")String clientId);

    boolean updateById(DataFunctionEntity param);

    int insertFunc(DataFunctionEntity param);

    DataFunctionEntity getById(@Param("id")Long id);

    @Pages
    List<DataFunctionAuthorityEntity> getAuthorityList(DataFunctionAuthorityQuery params);

    int insertFuncAuthority(DataFunctionAuthorityEntity params);

    boolean updateFuncAuthorityById(DataFunctionAuthorityEntity params);

    boolean updateFuncAuthorityStatus(@Param("clientId") String clientId,@Param("funcCode") String funcCode,@Param("dbStatus") int dbStatus);

    DataFunctionAuthorityEntity getFuncAuthorityById(@Param("id")Long id);
}