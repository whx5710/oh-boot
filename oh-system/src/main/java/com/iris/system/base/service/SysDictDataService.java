package com.iris.system.base.service;

import com.iris.system.base.entity.SysDictDataEntity;
import com.iris.system.base.query.SysDictDataQuery;
import com.iris.system.base.vo.SysDictDataVO;
import com.iris.framework.common.utils.PageResult;

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