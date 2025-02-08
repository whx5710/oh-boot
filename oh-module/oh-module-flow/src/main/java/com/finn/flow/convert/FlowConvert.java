package com.finn.flow.convert;

import com.finn.flow.entity.FlowEntity;
import com.finn.flow.vo.FlowVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 自定义流程表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-12-19
*/
@Mapper
public interface FlowConvert {
    FlowConvert INSTANCE = Mappers.getMapper(FlowConvert.class);

    FlowEntity convert(FlowVO vo);

    FlowVO convert(FlowEntity entity);

    List<FlowVO> convertList(List<FlowEntity> list);

}