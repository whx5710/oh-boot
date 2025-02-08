package com.finn.support.convert;

import com.finn.support.vo.SysDictDataSingleVO;
import com.finn.support.vo.SysDictDataVO;
import com.finn.support.entity.SysDictDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysDictDataConvert {
    SysDictDataConvert INSTANCE = Mappers.getMapper(SysDictDataConvert.class);

    SysDictDataVO convert(SysDictDataEntity entity);

    SysDictDataEntity convert(SysDictDataVO vo);
    
    List<SysDictDataVO> convertList(List<SysDictDataEntity> list);

    SysDictDataSingleVO convertSingle(SysDictDataVO vo);

}
