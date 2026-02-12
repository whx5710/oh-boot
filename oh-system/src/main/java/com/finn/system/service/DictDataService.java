package com.finn.system.service;

import com.finn.core.utils.PageResult;
import com.finn.system.entity.DictDataEntity;
import com.finn.system.query.DictDataQuery;
import com.finn.system.vo.DictDataVO;

import java.util.List;

/**
 * 数据字典
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface DictDataService {

    PageResult<DictDataVO> page(DictDataQuery query);

    void save(DictDataVO vo);

    void update(DictDataVO vo);

    void delete(List<Long> idList);

    DictDataEntity getById(Long id);

}