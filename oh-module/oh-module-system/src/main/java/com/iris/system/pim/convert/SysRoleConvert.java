package com.iris.system.pim.convert;

import com.iris.system.pim.entity.SysRoleEntity;
import com.iris.system.pim.vo.SysRoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysRoleConvert {
    SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

    SysRoleVO convert(SysRoleEntity entity);

    SysRoleEntity convert(SysRoleVO vo);
    
    List<SysRoleVO> convertList(List<SysRoleEntity> list);

}
