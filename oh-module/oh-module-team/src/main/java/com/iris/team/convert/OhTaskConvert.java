package com.iris.team.convert;

import com.iris.team.entity.OhTaskEntity;
import com.iris.team.vo.OhTaskVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 任务表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Mapper
public interface OhTaskConvert {
    OhTaskConvert INSTANCE = Mappers.getMapper(OhTaskConvert.class);

    OhTaskEntity convert(OhTaskVO vo);

    OhTaskVO convert(OhTaskEntity entity);

    List<OhTaskVO> convertList(List<OhTaskEntity> list);

}