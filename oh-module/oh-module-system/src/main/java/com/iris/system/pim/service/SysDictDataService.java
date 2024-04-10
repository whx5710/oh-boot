package com.iris.system.pim.service;

import com.iris.system.pim.entity.SysDictDataEntity;
import com.iris.system.pim.vo.SysDictDataVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.BaseService;
import com.iris.system.pim.query.SysDictDataQuery;

import java.util.List;

/**
 * 数据字典
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SysDictDataService extends BaseService<SysDictDataEntity> {

    PageResult<SysDictDataVO> page(SysDictDataQuery query);

    void save(SysDictDataVO vo);

    void update(SysDictDataVO vo);

    void delete(List<Long> idList);

}