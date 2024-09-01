package com.iris.system.base.convert;

import com.iris.system.base.vo.SysMenuTreeVO;
import com.iris.system.base.entity.SysMenuEntity;
import com.iris.system.base.vo.SysMenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SysMenuConvert {
    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    SysMenuEntity convert(SysMenuTreeVO vo);

    SysMenuTreeVO convert(SysMenuEntity entity);

    List<SysMenuTreeVO> convertTreeList(List<SysMenuEntity> list);

    List<SysMenuVO> convertList(List<SysMenuEntity> list);

}
