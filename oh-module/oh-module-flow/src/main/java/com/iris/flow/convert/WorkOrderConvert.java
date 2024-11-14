package com.iris.flow.convert;

import com.iris.flow.entity.WorkOrderEntity;
import com.iris.flow.vo.WorkOrderVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 工单表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-02-23
*/
@Mapper
public interface WorkOrderConvert {
    WorkOrderConvert INSTANCE = Mappers.getMapper(WorkOrderConvert.class);

    WorkOrderEntity convert(WorkOrderVO vo);

    WorkOrderVO convert(WorkOrderEntity entity);

    List<WorkOrderVO> convertList(List<WorkOrderEntity> list);

}