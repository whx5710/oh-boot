package com.iris.app.service;

import com.iris.app.entity.DataFunctionEntity;
import com.iris.app.query.DataFunctionQuery;
import com.iris.app.vo.DataFunctionVO;
import com.iris.core.utils.PageResult;

import java.util.List;

/**
 * 功能列表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-30
 */
public interface DataFunctionService {

    PageResult<DataFunctionVO> page(DataFunctionQuery query);

    PageResult<DataFunctionVO> pageByClientId(DataFunctionQuery query);

    DataFunctionEntity getById(Long id);

    List<DataFunctionVO> list(DataFunctionQuery query);

    void save(DataFunctionVO vo);

    void update(DataFunctionVO vo);

    void delete(List<Long> idList);
}