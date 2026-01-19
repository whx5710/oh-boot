package com.finn.system.convert;

import com.finn.system.entity.DeptEntity;
import com.finn.system.vo.DeptVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface DeptConvert {
    DeptConvert INSTANCE = Mappers.getMapper(DeptConvert.class);

    DeptEntity convert(DeptVO vo);

    DeptVO convert(DeptEntity entity);

    List<DeptVO> convertList(List<DeptEntity> list);

}
