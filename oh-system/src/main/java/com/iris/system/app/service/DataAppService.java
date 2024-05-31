package com.iris.system.app.service;

import com.iris.framework.common.entity.api.DataAppDTO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.BaseService;
import com.iris.system.app.entity.DataAppEntity;
import com.iris.system.app.query.DataAppQuery;
import com.iris.system.app.query.DataFunctionAuthorityQuery;

import java.util.List;

/**
 * 客户端
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */
public interface DataAppService extends BaseService<DataAppEntity> {

    PageResult<DataAppDTO> page(DataAppQuery query);

    void save(DataAppDTO vo);

    void update(DataAppDTO vo);

    void delete(List<Long> idList);

    List<DataAppDTO> listAuthority(DataFunctionAuthorityQuery params);

    DataAppDTO findByClientId(String clientId);
}