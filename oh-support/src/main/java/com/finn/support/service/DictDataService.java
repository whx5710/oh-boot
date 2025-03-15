package com.finn.support.service;

import com.finn.support.entity.DictDataEntity;
import com.finn.support.query.DictDataQuery;
import com.finn.support.vo.DictDataVO;
import com.finn.core.utils.PageResult;

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