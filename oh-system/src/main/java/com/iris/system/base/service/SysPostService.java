package com.iris.system.base.service;

import com.iris.system.base.entity.SysPostEntity;
import com.iris.system.base.query.SysPostQuery;
import com.iris.system.base.vo.SysPostVO;
import com.iris.framework.common.utils.PageResult;

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