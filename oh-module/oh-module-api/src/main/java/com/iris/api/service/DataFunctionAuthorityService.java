package com.iris.api.service;

import com.iris.api.entity.DataFunctionAuthorityEntity;
import com.iris.api.query.DataFunctionAuthorityQuery;
import com.iris.api.vo.DataFunctionAuthorityVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;

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