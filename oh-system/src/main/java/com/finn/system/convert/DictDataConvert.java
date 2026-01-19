package com.finn.system.convert;

import com.finn.system.entity.DictDataEntity;
import com.finn.system.vo.DictDataSingleVO;
import com.finn.system.vo.DictDataVO;
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
