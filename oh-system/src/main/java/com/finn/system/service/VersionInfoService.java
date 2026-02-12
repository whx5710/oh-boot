package com.finn.system.service;

import com.finn.core.utils.PageResult;
import com.finn.system.entity.VersionInfoEntity;
import com.finn.system.query.VersionInfoQuery;
import com.finn.system.vo.VersionInfoVO;

import java.util.List;

/**
 * 版本信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-09-16
 */
public interface VersionInfoService {

    PageResult<VersionInfoVO> page(VersionInfoQuery query);

    void save(VersionInfoVO vo);

    void update(VersionInfoVO vo);

    void delete(List<Long> idList);

    // 最新版本
    VersionInfoEntity latestVersion();

    VersionInfoEntity getById(Long id);
}