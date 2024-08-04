package com.iris.system.app.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.system.app.entity.DataFunctionEntity;
import com.iris.system.app.query.DataFunctionQuery;
import com.iris.system.app.vo.DataFunctionVO;

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