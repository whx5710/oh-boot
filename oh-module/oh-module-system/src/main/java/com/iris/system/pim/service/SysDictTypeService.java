package com.iris.system.pim.service;

import com.iris.system.pim.entity.SysDictTypeEntity;
import com.iris.system.pim.vo.SysDictTypeVO;
import com.iris.system.pim.vo.SysDictVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.BaseService;
import com.iris.system.pim.query.SysDictTypeQuery;

import java.util.List;

/**
 * 数据字典
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SysDictTypeService extends BaseService<SysDictTypeEntity> {

    PageResult<SysDictTypeVO> page(SysDictTypeQuery query);

    void save(SysDictTypeVO vo);

    void update(SysDictTypeVO vo);

    void delete(List<Long> idList);

    /**
     * 获取动态SQL数据
     */
    List<SysDictVO.DictData> getDictSql(Long id);

    /**
     * 获取全部字典列表
     */
    List<SysDictVO> getDictList();

    /**
     * 刷新字典缓存
     */
    void refreshTransCache();

}