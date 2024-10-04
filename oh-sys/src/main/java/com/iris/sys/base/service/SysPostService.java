package com.iris.sys.base.service;

import com.iris.sys.base.entity.SysPostEntity;
import com.iris.sys.base.query.SysPostQuery;
import com.iris.sys.base.vo.SysPostVO;
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