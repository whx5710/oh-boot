package com.finn.support.convert;

import com.finn.support.vo.DictDataSingleVO;
import com.finn.support.vo.DictDataVO;
import com.finn.support.entity.DictDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DictDataConvert {
    DictDataConvert INSTANCE = Mappers.getMapper(DictDataConvert.class);

    DictDataVO convert(DictDataEntity entity);

    DictDataEntity convert(DictDataVO vo);
    
    List<DictDataVO> convertList(List<DictDataEntity> list);

    DictDataSingleVO convertSingle(DictDataVO vo);

}
