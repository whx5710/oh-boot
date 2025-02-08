package com.finn.support.service;

import com.finn.support.entity.SysDictDataEntity;
import com.finn.support.query.SysDictDataQuery;
import com.finn.support.vo.SysDictDataVO;
import com.finn.core.utils.PageResult;

import java.util.List;

/**
 * 数据字典
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SysDictDataService {

    PageResult<SysDictDataVO> page(SysDictDataQuery query);

    void save(SysDictDataVO vo);

    void update(SysDictDataVO vo);

    void delete(List<Long> idList);

    SysDictDataEntity getById(Long id);

}