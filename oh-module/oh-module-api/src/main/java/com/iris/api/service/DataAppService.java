package com.iris.api.service;

import com.iris.api.entity.DataAppEntity;
import com.iris.api.query.DataAppQuery;
import com.iris.api.query.DataFunctionAuthorityQuery;
import com.iris.api.vo.DataAppVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.BaseService;

import java.util.List;

/**
 * 客户端
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */
public interface DataAppService extends BaseService<DataAppEntity> {

    PageResult<DataAppVO> page(DataAppQuery query);

    void save(DataAppVO vo);

    void update(DataAppVO vo);

    void delete(List<Long> idList);

    List<DataAppVO> listAuthority(DataFunctionAuthorityQuery params);

    DataAppVO findByClientId(String clientId);
}