package com.finn.support.convert;

import com.finn.support.entity.DeptEntity;
import com.finn.support.vo.DeptVO;
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
