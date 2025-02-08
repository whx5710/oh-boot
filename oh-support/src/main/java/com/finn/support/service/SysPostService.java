package com.finn.support.service;

import com.finn.support.entity.SysPostEntity;
import com.finn.support.query.SysPostQuery;
import com.finn.support.vo.SysPostVO;
import com.finn.core.utils.PageResult;

import java.util.List;

/**
 * 岗位管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SysPostService {

    PageResult<SysPostVO> page(SysPostQuery query);

    List<SysPostVO> getList();

    void save(SysPostVO vo);

    void update(SysPostVO vo);

    void delete(List<Long> idList);

    SysPostEntity getById(Long id);
}