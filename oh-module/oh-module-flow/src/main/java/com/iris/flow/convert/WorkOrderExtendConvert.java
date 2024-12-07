package com.iris.flow.convert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.iris.core.utils.JsonUtils;
import com.iris.flow.entity.WorkOrderEntity;
import com.iris.flow.vo.WorkOrderVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkOrderExtendConvert implements WorkOrderConvert{

    private final WorkOrderConvert workOrderConvert;

    public WorkOrderExtendConvert(WorkOrderConvert workOrderConvert){
        this.workOrderConvert = workOrderConvert;
    }

    @Override
    public WorkOrderEntity convert(WorkOrderVO vo) {
        WorkOrderEntity entity = workOrderConvert.convert(vo);
        if(vo.getExtendJsonMap() != null){
            entity.setExtendJson(JsonUtils.toJsonString(vo.getExtendJsonMap()));
        }
        return entity;
    }

    @Override
    public WorkOrderVO convert(WorkOrderEntity entity) {
        WorkOrderVO workOrderVO = workOrderConvert.convert(entity);
        if(entity.getExtendJson() != null && !entity.getExtendJson().isEmpty()){
            workOrderVO.setExtendJsonMap(JsonUtils.parseObject(entity.getExtendJson(), new TypeReference<Map<String, Object>>() {}));
        }
        return workOrderVO;
    }

    @Override
    public List<WorkOrderVO> convertList(List<WorkOrderEntity> list) {
        if ( list == null ) {
            return null;
        }
        List<WorkOrderVO> workOrderVOList = new ArrayList<WorkOrderVO>( list.size() );
        for ( WorkOrderEntity workOrderEntity : list ) {
            workOrderVOList.add(convert( workOrderEntity ));
        }
        return workOrderVOList;
    }
}
