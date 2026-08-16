package com.finn.system.convert;

import com.finn.system.entity.RoleEntity;
import com.finn.system.vo.RoleVO;
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
