package com.iris.sys.base.service;

import com.iris.core.utils.PageResult;
import com.iris.sys.base.entity.TenantMemberEntity;
import com.iris.sys.base.query.TenantMemberQuery;
import com.iris.sys.base.vo.TenantMemberVO;

/**
 * 租户信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2025-01-18
 */
public interface TenantMemberService {

    PageResult<TenantMemberVO> page(TenantMemberQuery query);

    void save(TenantMemberVO vo);

    void update(TenantMemberVO vo);

    TenantMemberEntity getById(Long id);
}
