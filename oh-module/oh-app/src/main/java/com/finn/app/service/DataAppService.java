package com.finn.app.service;

import com.finn.app.entity.DataAppEntity;
import com.finn.app.query.DataAppQuery;
import com.finn.common.entity.PageResult;
import com.finn.common.entity.DataAppDTO;

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

    DataAppDTO findByClientId(String clientId);

    DataAppEntity getById(Long id);
}