package com.finn.app.service;

import com.finn.app.entity.DataFunctionAuthorityEntity;
import com.finn.app.query.DataFunctionAuthorityQuery;
import com.finn.app.vo.DataFunctionAuthorityVO;
import com.finn.core.utils.PageResult;

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