package com.finn.support.service;

import com.finn.support.entity.SysDictTypeEntity;
import com.finn.support.query.SysDictTypeQuery;
import com.finn.support.vo.SysDictTypeVO;
import com.finn.support.vo.SysDictVO;
import com.finn.core.utils.PageResult;

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