package com.finn.flow.convert;

import com.finn.flow.entity.FlowNodeEntity;
import com.finn.flow.vo.FlowNodeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 环节定义表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-01-31
*/
@Mapper
public interface FlowNodeConvert {
    FlowNodeConvert INSTANCE = Mappers.getMapper(FlowNodeConvert.class);

    FlowNodeEntity convert(FlowNodeVO vo);

    FlowNodeVO convert(FlowNodeEntity entity);

    List<FlowNodeVO> convertList(List<FlowNodeEntity> list);

}