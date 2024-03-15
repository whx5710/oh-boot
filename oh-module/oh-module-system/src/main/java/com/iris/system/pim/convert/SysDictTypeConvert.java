package com.iris.system.pim.convert;

import com.iris.system.pim.entity.SysDictTypeEntity;
import com.iris.system.pim.vo.SysDictTypeVO;
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
