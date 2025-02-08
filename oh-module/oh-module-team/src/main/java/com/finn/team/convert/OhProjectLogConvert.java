package com.finn.team.convert;

import com.finn.team.entity.OhProjectLogEntity;
import com.finn.team.vo.OhProjectLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 项目、任务操作日志
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Mapper
public interface OhProjectLogConvert {
    OhProjectLogConvert INSTANCE = Mappers.getMapper(OhProjectLogConvert.class);

    OhProjectLogEntity convert(OhProjectLogVO vo);

    OhProjectLogVO convert(OhProjectLogEntity entity);

    List<OhProjectLogVO> convertList(List<OhProjectLogEntity> list);

}