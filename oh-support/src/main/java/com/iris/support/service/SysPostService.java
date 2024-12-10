package com.iris.support.service;

import com.iris.support.entity.SysPostEntity;
import com.iris.support.query.SysPostQuery;
import com.iris.support.vo.SysPostVO;
import com.iris.core.utils.PageResult;

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