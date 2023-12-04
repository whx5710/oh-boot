package com.iris.team.convert;

import com.iris.team.vo.OhProjectLogVO;
import com.iris.team.entity.OhProjectLogEntity;
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