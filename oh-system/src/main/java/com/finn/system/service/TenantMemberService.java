package com.finn.system.service;

import com.finn.core.utils.PageResult;
import com.finn.system.entity.TenantMemberEntity;
import com.finn.system.query.TenantMemberQuery;
import com.finn.system.vo.TenantMemberVO;

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

    void delete(Long id);
}
