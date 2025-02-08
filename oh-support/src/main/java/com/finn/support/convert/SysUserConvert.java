package com.finn.support.convert;

import com.finn.core.entity.BaseUserEntity;
import com.finn.framework.security.user.UserDetail;
import com.finn.support.vo.SysUserExcelVO;
import com.finn.support.vo.SysUserVO;
import com.finn.support.entity.SysUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SysUserConvert {
    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUserVO convert(SysUserEntity entity);

    SysUserEntity convert(SysUserVO vo);

    SysUserVO convert(UserDetail userDetail);

    UserDetail convertDetail(SysUserEntity entity);

    List<SysUserVO> convertList(List<SysUserEntity> list);

    List<SysUserExcelVO> convert2List(List<SysUserEntity> list);

    List<SysUserEntity> convertListEntity(List<SysUserExcelVO> list);

    BaseUserEntity convertSimpleVO(SysUserEntity entity);

}
