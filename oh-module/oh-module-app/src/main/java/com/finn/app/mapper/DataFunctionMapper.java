package com.finn.app.mapper;

import com.finn.app.entity.DataFunctionAuthorityEntity;
import com.finn.app.entity.DataFunctionEntity;
import com.finn.app.query.DataFunctionAuthorityQuery;
import com.finn.app.query.DataFunctionQuery;
import com.finn.app.vo.DataFunctionVO;
import com.finn.framework.aop.annotations.Pages;
import com.finn.framework.datasource.mapper.BaseMapper;
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
public interface DataFunctionMapper extends BaseMapper<DataFunctionEntity> {

    List<DataFunctionEntity> getList(DataFunctionQuery query);

    List<DataFunctionVO> pageByClientId(@Param("clientId")String clientId);

    DataFunctionEntity getById(@Param("id")Long id);

    @Pages
    List<DataFunctionAuthorityEntity> getAuthorityList(DataFunctionAuthorityQuery params);

    DataFunctionAuthorityEntity getFuncAuthorityById(@Param("id")Long id);
}