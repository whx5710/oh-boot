package com.finn.app.mapper;

import com.finn.framework.aop.annotations.Pages;
import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.common.entity.DataAppDTO;
import com.finn.app.entity.DataAppEntity;
import com.finn.app.query.DataAppQuery;
import com.finn.app.query.DataFunctionAuthorityQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 客户端
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-29
*/
@Mapper
public interface DataAppMapper extends BaseMapper<DataAppEntity> {

    List<DataAppDTO> listAuthority(@Param("params") DataFunctionAuthorityQuery params);

    @Pages
    List<DataAppEntity> getList(DataAppQuery dataAppQuery);

}