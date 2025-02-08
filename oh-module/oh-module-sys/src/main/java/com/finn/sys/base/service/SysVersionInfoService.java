package com.finn.sys.base.service;

import com.finn.core.utils.PageResult;
import com.finn.sys.base.entity.SysVersionInfoEntity;
import com.finn.sys.base.query.SysVersionInfoQuery;
import com.finn.sys.base.vo.SysVersionInfoVO;

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