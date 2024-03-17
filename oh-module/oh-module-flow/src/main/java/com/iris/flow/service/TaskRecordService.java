package com.iris.flow.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;
import com.iris.flow.entity.TaskRecordEntity;
import com.iris.flow.query.TaskRecordQuery;
import com.iris.flow.vo.TaskRecordVO;

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

    /**
     * 保存环节运行记录表，在执行完 complete或启动流程 后再执行该方法
     * 1、更新上个任务的数据
     * 2、新增当前任务
     * @param proInsId 环节实例ID
     * @param taskId 上一环节任务ID
     * @return
     */
    List<TaskRecordVO> saveTaskRecord(String proInsId, String taskId);
}