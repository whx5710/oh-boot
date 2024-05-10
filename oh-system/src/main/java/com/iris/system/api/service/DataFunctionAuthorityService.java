package com.iris.system.api.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.BaseService;
import com.iris.system.api.entity.DataFunctionAuthorityEntity;
import com.iris.system.api.query.DataFunctionAuthorityQuery;
import com.iris.system.api.vo.DataFunctionAuthorityVO;

import java.util.List;

/**
 * 客户端接口授权
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */
public interface DataFunctionAuthorityService extends BaseService<DataFunctionAuthorityEntity> {

    PageResult<DataFunctionAuthorityVO> page(DataFunctionAuthorityQuery query);

    void save(DataFunctionAuthorityVO vo);

    void update(DataFunctionAuthorityVO vo);

    void delete(List<Long> idList);

    void make(DataFunctionAuthorityVO data);
}