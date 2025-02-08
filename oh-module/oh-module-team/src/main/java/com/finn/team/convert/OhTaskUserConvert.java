package com.finn.team.convert;

import com.finn.team.entity.OhTaskUserEntity;
import com.finn.team.vo.OhTaskUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 任务人员表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Mapper
public interface OhTaskUserConvert {
    OhTaskUserConvert INSTANCE = Mappers.getMapper(OhTaskUserConvert.class);

    OhTaskUserEntity convert(OhTaskUserVO vo);

    OhTaskUserVO convert(OhTaskUserEntity entity);

    List<OhTaskUserVO> convertList(List<OhTaskUserEntity> list);

}