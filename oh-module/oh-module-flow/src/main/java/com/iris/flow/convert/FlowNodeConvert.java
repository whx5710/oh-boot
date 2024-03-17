package com.iris.flow.convert;

import com.iris.flow.entity.FlowNodeEntity;
import com.iris.flow.vo.FlowNodeVO;
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