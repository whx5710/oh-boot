package com.finn.flow.convert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.finn.core.utils.JsonUtils;
import com.finn.flow.entity.WorkOrderEntity;
import com.finn.flow.vo.WorkOrderVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义转换
 * json字符串转map
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-12-13
 */
public class WorkOrderExtendConvert implements WorkOrderConvert{

    private final WorkOrderConvert workOrderConvert;

    public WorkOrderExtendConvert(WorkOrderConvert workOrderConvert){
        this.workOrderConvert = workOrderConvert;
    }

    @Override
    public WorkOrderEntity convert(WorkOrderVO vo) {
        WorkOrderEntity entity = workOrderConvert.convert(vo);
        // map转json字符串
        if(vo.getExtendJsonMap() != null){
            entity.setExtendJson(JsonUtils.toJsonString(vo.getExtendJsonMap()));
        }
        return entity;
    }

    @Override
    public WorkOrderVO convert(WorkOrderEntity entity) {
        WorkOrderVO workOrderVO = workOrderConvert.convert(entity);
        // 实体类转成VO时，扩展的json字符串转成map对象
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
        List<WorkOrderVO> workOrderVOList = new ArrayList<WorkOrderVO>(list.size());
        for ( WorkOrderEntity workOrderEntity : list ) {
            workOrderVOList.add(convert( workOrderEntity ));
        }
        return workOrderVOList;
    }
}
