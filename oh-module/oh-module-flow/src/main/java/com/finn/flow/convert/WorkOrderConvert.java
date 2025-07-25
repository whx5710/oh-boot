package com.finn.flow.convert;

import com.finn.flow.entity.WorkOrderEntity;
import com.finn.flow.vo.WorkOrderVO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 工单表
* 自定义转换
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-02-23
*/
@Mapper
@DecoratedWith(WorkOrderExtendConvert.class) // 指定实现类
public interface WorkOrderConvert {
    WorkOrderConvert INSTANCE = Mappers.getMapper(WorkOrderConvert.class);

    WorkOrderEntity convert(WorkOrderVO vo);

    WorkOrderVO convert(WorkOrderEntity entity);

    List<WorkOrderVO> convertList(List<WorkOrderEntity> list);

}