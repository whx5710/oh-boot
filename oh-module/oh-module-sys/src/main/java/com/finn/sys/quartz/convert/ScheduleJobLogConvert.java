package com.finn.sys.quartz.convert;

import com.finn.sys.quartz.entity.ScheduleJobLogEntity;
import com.finn.sys.quartz.vo.ScheduleJobLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 定时任务日志
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface ScheduleJobLogConvert {
    ScheduleJobLogConvert INSTANCE = Mappers.getMapper(ScheduleJobLogConvert.class);

    ScheduleJobLogEntity convert(ScheduleJobLogVO vo);

    ScheduleJobLogVO convert(ScheduleJobLogEntity entity);

    List<ScheduleJobLogVO> convertList(List<ScheduleJobLogEntity> list);

}