package com.iris.sys.app.service;

import com.iris.framework.common.entity.api.DataAppDTO;
import com.iris.framework.common.utils.PageResult;
import com.iris.sys.app.entity.DataAppEntity;
import com.iris.sys.app.query.DataAppQuery;
import com.iris.sys.app.query.DataFunctionAuthorityQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户端
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */
public interface DataAppService{

    PageResult<DataAppDTO> page(DataAppQuery query);

    void save(DataAppDTO vo);

    void update(DataAppDTO vo);

    void delete(List<Long> idList);

    List<DataAppDTO> listAuthority(DataFunctionAuthorityQuery params);

    DataAppDTO findByClientId(String clientId);

    DataAppEntity getById(@Param("id")Long id);
}