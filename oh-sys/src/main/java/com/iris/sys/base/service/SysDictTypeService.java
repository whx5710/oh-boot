package com.iris.sys.base.service;

import com.iris.sys.base.entity.SysDictTypeEntity;
import com.iris.sys.base.query.SysDictTypeQuery;
import com.iris.sys.base.vo.SysDictTypeVO;
import com.iris.sys.base.vo.SysDictVO;
import com.iris.framework.common.utils.PageResult;

import java.util.List;

/**
 * 数据字典
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SysDictTypeService {

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

    SysDictTypeEntity getById(Long id);

}