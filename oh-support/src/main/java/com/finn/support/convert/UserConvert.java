package com.finn.support.convert;

import com.finn.core.entity.BaseUserEntity;
import com.finn.framework.security.user.UserDetail;
import com.finn.support.vo.UserExcelVO;
import com.finn.support.vo.UserVO;
import com.finn.support.entity.UserEntity;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
@DecoratedWith(UserExtConvert.class) // 指定实现类
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserVO convert(UserEntity entity);

    UserEntity convert(UserVO vo);

    UserVO convert(UserDetail userDetail);

    UserDetail convertDetail(UserEntity entity);

    List<UserVO> convertList(List<UserEntity> list);

    List<UserExcelVO> convert2List(List<UserEntity> list);

    UserExcelVO convert2Excel(UserEntity entity);

    UserEntity convert(UserExcelVO vo);

    List<UserEntity> convertListEntity(List<UserExcelVO> list);

    BaseUserEntity convertSimpleVO(UserEntity entity);

}
