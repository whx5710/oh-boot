package com.finn.system.service;

import com.finn.core.utils.PageResult;
import com.finn.system.entity.DeptEntity;
import com.finn.system.query.DeptQuery;
import com.finn.system.vo.DeptVO;

import java.util.List;

/**
 * 部门管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface DeptService {

    List<DeptVO> getList(DeptQuery query);

    PageResult<DeptVO> page(DeptQuery query);

    void save(DeptVO vo);

    void update(DeptVO vo);

    void updateById(DeptEntity dept);

    void delete(Long id);

    /**
     * 根据部门ID，获取子部门ID列表(包含本部门ID)
     * @param id   部门ID
     */
    List<Long> getSubDeptIdList(Long id);

    DeptEntity getById(Long id);

    /**
     * 根据ID获取部门信息
     * @param id 用户ID
     * @param cache 为true则优先读取缓存
     * @return 部门信息
     */
    DeptEntity getById(Long id, Boolean cache);
}