package com.finn.system.convert;

import com.finn.system.entity.DictTypeEntity;
import com.finn.system.vo.DictTypeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DictTypeConvert {
    DictTypeConvert INSTANCE = Mappers.getMapper(DictTypeConvert.class);

    DictTypeVO convert(DictTypeEntity entity);

    DictTypeEntity convert(DictTypeVO vo);
    
    List<DictTypeVO> convertList(List<DictTypeEntity> list);

}
