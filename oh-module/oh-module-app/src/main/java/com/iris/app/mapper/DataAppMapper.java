package com.iris.app.mapper;

import com.iris.framework.datasource.annotations.Pages;
import com.iris.framework.entity.api.DataAppDTO;
import com.iris.app.entity.DataAppEntity;
import com.iris.app.query.DataAppQuery;
import com.iris.app.query.DataFunctionAuthorityQuery;
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
public interface DataAppMapper {
    List<DataAppDTO> listAuthority(@Param("params") DataFunctionAuthorityQuery params);

    @Pages
    List<DataAppEntity> getList(DataAppQuery dataAppQuery);

    int insertDataApp(DataAppEntity dataAppEntity);

    boolean updateById(DataAppEntity dataAppEntity);

    DataAppEntity getById(@Param("id")Long id);
}