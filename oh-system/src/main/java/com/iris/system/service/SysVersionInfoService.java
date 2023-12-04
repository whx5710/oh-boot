package com.iris.system.service;

import com.iris.framework.mybatis.service.BaseService;
import com.iris.framework.common.utils.PageResult;
import com.iris.system.vo.SysVersionInfoVO;
import com.iris.system.query.SysVersionInfoQuery;
import com.iris.system.entity.SysVersionInfoEntity;

import java.util.List;

/**
 * 版本信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-09-16
 */
public interface SysVersionInfoService extends BaseService<SysVersionInfoEntity> {

    PageResult<SysVersionInfoVO> page(SysVersionInfoQuery query);

    void save(SysVersionInfoVO vo);

    void update(SysVersionInfoVO vo);

    void delete(List<Long> idList);

    // 最新版本
    SysVersionInfoEntity latestVersion();
}