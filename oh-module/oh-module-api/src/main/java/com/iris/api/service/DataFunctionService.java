package com.iris.api.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;
import com.iris.api.vo.DataFunctionVO;
import com.iris.api.query.DataFunctionQuery;
import com.iris.api.entity.DataFunctionEntity;

import java.util.List;

/**
 * 功能列表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-30
 */
public interface DataFunctionService extends BaseService<DataFunctionEntity> {

    PageResult<DataFunctionVO> page(DataFunctionQuery query);

    PageResult<DataFunctionVO> pageByClientId(DataFunctionQuery query);



    List<DataFunctionVO> list(DataFunctionQuery query);

    void save(DataFunctionVO vo);

    void update(DataFunctionVO vo);

    void delete(List<Long> idList);
}