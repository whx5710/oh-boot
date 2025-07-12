package com.finn.support.convert;

import com.finn.support.vo.RoleVO;
import com.finn.support.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleConvert {
    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    RoleVO convert(RoleEntity entity);

    RoleEntity convert(RoleVO vo);
    
    List<RoleVO> convertList(List<RoleEntity> list);

}
