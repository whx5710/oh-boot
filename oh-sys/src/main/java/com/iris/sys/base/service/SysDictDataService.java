package com.iris.sys.base.service;

import com.iris.sys.base.entity.SysDictDataEntity;
import com.iris.sys.base.query.SysDictDataQuery;
import com.iris.sys.base.vo.SysDictDataVO;
import com.iris.core.utils.PageResult;

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