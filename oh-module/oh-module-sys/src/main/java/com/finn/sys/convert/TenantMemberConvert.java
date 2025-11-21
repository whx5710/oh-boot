package com.finn.sys.convert;

import com.finn.sys.entity.TenantMemberEntity;
import com.finn.sys.vo.TenantMemberVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 2025-01-18
 */
@Mapper
public interface TenantMemberConvert {
    TenantMemberConvert INSTANCE = Mappers.getMapper(TenantMemberConvert.class);

    TenantMemberEntity convert(TenantMemberVO vo);

    TenantMemberVO convert(TenantMemberEntity entity);

    List<TenantMemberVO> convertList(List<TenantMemberEntity> list);
}
