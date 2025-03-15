package com.finn.support.convert;

import com.finn.support.vo.DictTypeVO;
import com.finn.support.entity.DictTypeEntity;
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
