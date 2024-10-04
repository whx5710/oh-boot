package com.iris.sys.app.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.sys.app.entity.DataFunctionAuthorityEntity;
import com.iris.sys.app.query.DataFunctionAuthorityQuery;
import com.iris.sys.app.vo.DataFunctionAuthorityVO;

import java.util.List;

/**
 * 客户端接口授权
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */
public interface DataFunctionAuthorityService {

    PageResult<DataFunctionAuthorityVO> page(DataFunctionAuthorityQuery query);

    void save(DataFunctionAuthorityVO vo);

    void update(DataFunctionAuthorityVO vo);

    void delete(List<Long> idList);

    void make(DataFunctionAuthorityVO data);

    DataFunctionAuthorityEntity getById(Long id);
}