package com.iris.support.convert;

import com.iris.core.entity.BaseUserEntity;
import com.iris.framework.security.user.UserDetail;
import com.iris.support.vo.SysUserExcelVO;
import com.iris.support.vo.SysUserVO;
import com.iris.support.entity.SysUserEntity;
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
