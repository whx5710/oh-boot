package com.iris.system.app.dao;

import com.iris.framework.common.constant.Constant;
import com.iris.framework.common.entity.api.DataAppDTO;
import com.iris.framework.datasource.annotations.Ds;
import com.iris.system.app.entity.DataAppEntity;
import com.iris.system.app.query.DataAppQuery;
import com.iris.system.app.query.DataFunctionAuthorityQuery;
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
@Ds(Constant.SYS_DB) // 初始化需缓存
public interface DataAppDao {
    List<DataAppDTO> listAuthority(@Param("params") DataFunctionAuthorityQuery params);

    List<DataAppEntity> getList(DataAppQuery dataAppQuery);

    int insertDataApp(DataAppEntity dataAppEntity);

    boolean updateById(DataAppEntity dataAppEntity);

    DataAppEntity getById(@Param("id")Long id);
}