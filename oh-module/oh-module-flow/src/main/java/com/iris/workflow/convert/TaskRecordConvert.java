package com.iris.workflow.convert;

import com.iris.workflow.entity.TaskRecordEntity;
import com.iris.workflow.vo.TaskRecordVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 环节运行记录表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-02-06
*/
@Mapper
public interface TaskRecordConvert {
    TaskRecordConvert INSTANCE = Mappers.getMapper(TaskRecordConvert.class);

    TaskRecordEntity convert(TaskRecordVO vo);

    TaskRecordVO convert(TaskRecordEntity entity);

    List<TaskRecordVO> convertList(List<TaskRecordEntity> list);

}