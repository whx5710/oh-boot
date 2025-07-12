package com.finn.team.convert;

import com.finn.team.entity.OhProjectEntity;
import com.finn.team.vo.OhProjectVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 项目信息表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Mapper
public interface OhProjectConvert {
    OhProjectConvert INSTANCE = Mappers.getMapper(OhProjectConvert.class);

    OhProjectEntity convert(OhProjectVO vo);

    OhProjectVO convert(OhProjectEntity entity);

    List<OhProjectVO> convertList(List<OhProjectEntity> list);

}