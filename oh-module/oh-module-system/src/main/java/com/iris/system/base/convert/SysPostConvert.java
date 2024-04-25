package com.iris.system.base.convert;

import com.iris.system.base.vo.SysPostVO;
import com.iris.system.base.entity.SysPostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SysPostConvert {
    SysPostConvert INSTANCE = Mappers.getMapper(SysPostConvert.class);

    SysPostVO convert(SysPostEntity entity);

    SysPostEntity convert(SysPostVO vo);

    List<SysPostVO> convertList(List<SysPostEntity> list);

}
