package com.iris.api.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.api.entity.DataAppEntity;
import com.iris.api.query.DataFunctionAuthorityQuery;
import com.iris.api.vo.DataAppVO;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.mybatis.dao.BaseDao;
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
@DS(Constant.SYS_DB)
public interface DataAppDao extends BaseDao<DataAppEntity> {
    List<DataAppVO> listAuthority(@Param("params") DataFunctionAuthorityQuery params);
}