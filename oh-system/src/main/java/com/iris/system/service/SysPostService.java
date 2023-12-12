package com.iris.system.service;

import com.iris.system.entity.SysPostEntity;
import com.iris.system.query.SysPostQuery;
import com.iris.system.vo.SysPostVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;

import java.util.List;

/**
 * 岗位管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SysPostService extends BaseService<SysPostEntity> {

    PageResult<SysPostVO> page(SysPostQuery query);

    List<SysPostVO> getList();

    void save(SysPostVO vo);

    void update(SysPostVO vo);

    void delete(List<Long> idList);
}