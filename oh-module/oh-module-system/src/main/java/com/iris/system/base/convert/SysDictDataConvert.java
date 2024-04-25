package com.iris.system.base.convert;

import com.iris.system.base.vo.SysDictDataVO;
import com.iris.system.base.entity.SysDictDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysDictDataConvert {
    SysDictDataConvert INSTANCE = Mappers.getMapper(SysDictDataConvert.class);

    SysDictDataVO convert(SysDictDataEntity entity);

    SysDictDataEntity convert(SysDictDataVO vo);
    
    List<SysDictDataVO> convertList(List<SysDictDataEntity> list);

}
