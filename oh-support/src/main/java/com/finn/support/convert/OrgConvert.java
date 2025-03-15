package com.finn.support.convert;

import com.finn.support.vo.OrgVO;
import com.finn.support.entity.OrgEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface OrgConvert {
    OrgConvert INSTANCE = Mappers.getMapper(OrgConvert.class);

    OrgEntity convert(OrgVO vo);

    OrgVO convert(OrgEntity entity);

    List<OrgVO> convertList(List<OrgEntity> list);

}
