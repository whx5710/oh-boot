package com.iris.system.quartz.convert;

import com.iris.system.quartz.entity.ScheduleJobEntity;
import com.iris.system.quartz.vo.ScheduleJobVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 定时任务
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface ScheduleJobConvert {
    ScheduleJobConvert INSTANCE = Mappers.getMapper(ScheduleJobConvert.class);

    ScheduleJobEntity convert(ScheduleJobVO vo);

    ScheduleJobVO convert(ScheduleJobEntity entity);

    List<ScheduleJobVO> convertList(List<ScheduleJobEntity> list);

}