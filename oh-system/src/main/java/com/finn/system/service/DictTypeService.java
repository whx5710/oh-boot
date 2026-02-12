package com.finn.system.service;

import com.finn.core.utils.PageResult;
import com.finn.system.entity.DictTypeEntity;
import com.finn.system.query.DictTypeQuery;
import com.finn.system.vo.DictTypeVO;
import com.finn.system.vo.DictVO;

import java.util.List;

/**
 * 数据字典
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface DictTypeService {

    PageResult<DictTypeVO> page(DictTypeQuery query);

    void save(DictTypeVO vo);

    void update(DictTypeVO vo);

    void delete(List<Long> idList);

    /**
     * 获取动态SQL数据
     */
    List<DictVO.DictData> getDictSql(Long id);

    /**
     * 获取全部字典列表
     */
    List<DictVO> getDictList();

    /**
     * 刷新字典缓存
     */
    void refreshTransCache();

    DictTypeEntity getById(Long id);

}