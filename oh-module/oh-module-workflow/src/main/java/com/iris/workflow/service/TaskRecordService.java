package com.iris.workflow.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;
import com.iris.workflow.entity.TaskRecordEntity;
import com.iris.workflow.query.TaskRecordQuery;
import com.iris.workflow.vo.TaskRecordVO;

import java.util.List;

/**
 * 环节运行表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-03
 */
public interface TaskRecordService extends BaseService<TaskRecordEntity> {

    PageResult<TaskRecordEntity> page(TaskRecordQuery query);

    boolean save(TaskRecordEntity vo);

    void update(TaskRecordVO vo);

    void delete(List<Long> idList);

    // 修改当前运行标志
    boolean updateRunMark(String procInstId);

    List<TaskRecordVO> taskList(TaskRecordQuery query);

    List<TaskRecordVO> saveTaskRecord(String proInsId, String taskId);
}