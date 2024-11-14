package com.iris.app.service;

import com.iris.app.entity.DataAppEntity;
import com.iris.app.query.DataAppQuery;
import com.iris.app.query.DataFunctionAuthorityQuery;
import com.iris.core.utils.PageResult;
import com.iris.framework.entity.api.DataAppDTO;
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