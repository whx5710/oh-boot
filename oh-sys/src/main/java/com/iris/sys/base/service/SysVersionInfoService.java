package com.iris.sys.base.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.sys.base.query.SysVersionInfoQuery;
import com.iris.sys.base.vo.SysVersionInfoVO;
import com.iris.sys.base.entity.SysVersionInfoEntity;

import java.util.List;

/**
 * 版本信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-09-16
 */
public interface SysVersionInfoService {

    PageResult<SysVersionInfoVO> page(SysVersionInfoQuery query);

    void save(SysVersionInfoVO vo);

    void update(SysVersionInfoVO vo);

    void delete(List<Long> idList);

    // 最新版本
    SysVersionInfoEntity latestVersion();

    SysVersionInfoEntity getById(Long id);
}