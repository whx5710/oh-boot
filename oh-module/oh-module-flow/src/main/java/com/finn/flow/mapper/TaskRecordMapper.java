package com.finn.flow.mapper;

import com.finn.flow.entity.TaskRecordEntity;
import com.finn.flow.query.TaskRecordQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 环节运行表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-02-03
*/
@Mapper
public interface TaskRecordMapper {

    // 修改当前运行标志
    boolean updateRunMark(@Param("procInstId") String procInstId);

    List<TaskRecordEntity> getTaskList(TaskRecordQuery query);

    int saveTaskRecord(TaskRecordEntity param);

    boolean updateTaskRecordById(TaskRecordEntity param);

    TaskRecordEntity getTaskRecordById(@Param("id")Long id);

}