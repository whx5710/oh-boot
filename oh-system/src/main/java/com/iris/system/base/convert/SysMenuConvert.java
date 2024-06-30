package com.iris.system.base.convert;

import com.iris.system.base.vo.SysMenuTreeVO;
import com.iris.system.base.entity.SysMenuEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SysMenuConvert {
    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    SysMenuEntity convert(SysMenuTreeVO vo);

    SysMenuTreeVO convert(SysMenuEntity entity);

    List<SysMenuTreeVO> convertList(List<SysMenuEntity> list);

}
