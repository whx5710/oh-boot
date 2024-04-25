package com.iris.system.base.convert;

import com.iris.system.base.vo.SysDictTypeVO;
import com.iris.system.base.entity.SysDictTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysDictTypeConvert {
    SysDictTypeConvert INSTANCE = Mappers.getMapper(SysDictTypeConvert.class);

    SysDictTypeVO convert(SysDictTypeEntity entity);

    SysDictTypeEntity convert(SysDictTypeVO vo);
    
    List<SysDictTypeVO> convertList(List<SysDictTypeEntity> list);

}
