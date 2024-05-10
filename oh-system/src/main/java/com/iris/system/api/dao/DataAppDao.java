package com.iris.system.api.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.common.entity.api.DataAppDTO;
import com.iris.framework.datasource.dao.BaseDao;
import com.iris.system.api.entity.DataAppEntity;
import com.iris.system.api.query.DataFunctionAuthorityQuery;
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
@DS(Constant.SYS_DB) // 初始化需缓存
public interface DataAppDao extends BaseDao<DataAppEntity> {
    List<DataAppDTO> listAuthority(@Param("params") DataFunctionAuthorityQuery params);
}