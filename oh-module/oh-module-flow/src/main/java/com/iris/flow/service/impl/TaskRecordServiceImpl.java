package com.iris.flow.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.flow.convert.TaskRecordConvert;
import com.iris.flow.entity.TaskRecordEntity;
import com.iris.flow.mapper.TaskRecordMapper;
import com.iris.flow.query.TaskRecordQuery;
import com.iris.flow.service.TaskRecordService;
import com.iris.flow.vo.TaskRecordVO;
import com.iris.core.utils.AssertUtils;
import com.iris.core.utils.PageResult;
import com.iris.framework.security.user.SecurityUser;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 环节运行表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-03
 */
@Service
public class TaskRecordServiceImpl implements TaskRecordService {

    private final HistoryService historyService;

    private final TaskRecordMapper taskRecordMapper;

    public TaskRecordServiceImpl(HistoryService historyService, TaskRecordMapper taskRecordMapper){
        this.historyService = historyService;
        this.taskRecordMapper = taskRecordMapper;
    }

    @Override
    public PageResult<TaskRecordEntity> page(TaskRecordQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<TaskRecordEntity> list = taskRecordMapper.getTaskList(new TaskRecordQuery());
        PageInfo<TaskRecordEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public boolean save(TaskRecordEntity vo) {
        taskRecordMapper.saveTaskRecord(vo);
        return true;
    }

    @Override
    public void update(TaskRecordVO vo) {
        taskRecordMapper.updateTaskRecordById(TaskRecordConvert.INSTANCE.convert(vo));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            TaskRecordEntity param = new TaskRecordEntity();
            param.setId(id);
            param.setDbStatus(0);
            taskRecordMapper.updateTaskRecordById(param);
        });
    }

    // 修改当前运行标志
    @Override
    public boolean updateRunMark(String procInstId) {
        return taskRecordMapper.updateRunMark(procInstId);
    }


    /**
     * 获取任务列表
     * @param query
     * @return
     */
    @Override
    public List<TaskRecordVO> taskList(TaskRecordQuery query) {
        return TaskRecordConvert.INSTANCE.convertList(taskRecordMapper.getTaskList(query));
    }


    /**
     * 保存环节运行记录表，在执行完 complete或启动流程 后再执行该方法
     * 1、更新上个任务的数据
     * 2、新增当前任务
     * @param proInsId 环节实例ID
     * @param taskId 上一环节任务ID
     * @return
     */
    @Override
    public List<TaskRecordVO> saveTaskRecord(String proInsId, String taskId){
        AssertUtils.isBlank(proInsId, "【参数异常】流程实例ID");
        // 正在运行的环节列表（未完成的任务）
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(proInsId).unfinished()
                .orderByHistoricActivityInstanceStartTime().asc()
                .orderByHistoricTaskInstanceEndTime().asc().list();
        List<TaskRecordVO> data = new ArrayList<>();
        if(!ObjectUtils.isEmpty(list)){
            for(HistoricTaskInstance his: list){
                TaskRecordEntity taskRecord = new TaskRecordEntity();
                taskRecord.setProcDefId(his.getProcessDefinitionId());
                taskRecord.setProcInstId(his.getProcessInstanceId());
                taskRecord.setActInstId(his.getActivityInstanceId());
                taskRecord.setTaskId(his.getId());
                taskRecord.setTaskDefId(his.getTaskDefinitionKey());
                taskRecord.setTaskName(his.getName());

                // 当前标识，默认0，1标识当前环节
                taskRecord.setRunMark((his.getEndTime()!=null && his.getDeleteReason()!= null && his.getDeleteReason().equalsIgnoreCase("completed"))?0:1);
                taskRecord.setAssignee(his.getAssignee());
                taskRecord.setAssigneeName(SecurityUser.getUser().getRealName());
                taskRecord.setStartTime(his.getStartTime());
                taskRecord.setEndTime(his.getEndTime());
                taskRecord.setDuration(his.getDurationInMillis());

                taskRecord.setFromActInstId("");
                taskRecord.setFromTaskDefId("");
                taskRecord.setFromTaskId("");
                taskRecord.setFromTaskName("");

                if(!ObjectUtils.isEmpty(taskId) && !taskId.equals(his.getId())){
                    // 处理上一任务，更新相关信息
                    List<HistoricTaskInstance> parentList = historyService.createHistoricTaskInstanceQuery()
                            .processInstanceId(proInsId).taskId(taskId).orderByHistoricActivityInstanceStartTime().desc()
                            .orderByHistoricTaskInstanceEndTime().desc().list();
                    if(!ObjectUtils.isEmpty(parentList)){
                        for(HistoricTaskInstance hisParent: parentList){
                            taskRecord.setFromActInstId(hisParent.getActivityInstanceId());
                            taskRecord.setFromTaskDefId(hisParent.getTaskDefinitionKey());
                            taskRecord.setFromTaskId(hisParent.getId());
                            taskRecord.setFromTaskName(hisParent.getName());

                            /************ 更新上一任务 *** start *********/
                            TaskRecordQuery query = new TaskRecordQuery();
                            query.setProcInstId(proInsId);
                            query.setRunMark(1);
                            if(!ObjectUtils.isEmpty(taskId)){
                                query.setTaskId(taskId);
                            }
                            List<TaskRecordVO> voList = taskList(query);
                            if(!ObjectUtils.isEmpty(voList)){
                                for (TaskRecordVO vo : voList){
                                    vo.setEndTime(hisParent.getEndTime());
                                    vo.setAssignee(hisParent.getAssignee());
                                    vo.setAssigneeName("");
                                    vo.setDuration(hisParent.getDurationInMillis());
                                    vo.setRunMark(0);
                                    update(vo);
                                }
                            }
                            /********** 更新上一任务 *** end *********/
                            data.add(TaskRecordConvert.INSTANCE.convert(taskRecord));
                            save(taskRecord);
                        }
                    }else{
                        data.add(TaskRecordConvert.INSTANCE.convert(taskRecord));
                        save(taskRecord);
                    }
                }else{
                    data.add(TaskRecordConvert.INSTANCE.convert(taskRecord));
                    save(taskRecord);
                }
            }
        }else{
            TaskRecordEntity taskRecord = new TaskRecordEntity();
            // 处理上一任务，更新相关信息
            List<HistoricTaskInstance> parentList = historyService.createHistoricTaskInstanceQuery()
                    .processInstanceId(proInsId).taskId(taskId).orderByHistoricActivityInstanceStartTime().desc()
                    .orderByHistoricTaskInstanceEndTime().desc().list();
            if(!ObjectUtils.isEmpty(parentList)){
                for(HistoricTaskInstance hisParent: parentList){
                    taskRecord.setFromActInstId(hisParent.getActivityInstanceId());
                    taskRecord.setFromTaskDefId(hisParent.getTaskDefinitionKey());
                    taskRecord.setFromTaskId(hisParent.getId());
                    taskRecord.setFromTaskName(hisParent.getName());

                    // 开始时间用上一任务的结束时间
                    taskRecord.setStartTime(hisParent.getEndTime());
                    taskRecord.setEndTime(hisParent.getEndTime());

                    taskRecord.setProcDefId(hisParent.getProcessDefinitionId());

                    /************ 更新上一任务 *** start *********/
                    TaskRecordQuery query = new TaskRecordQuery();
                    query.setProcInstId(proInsId);
                    query.setRunMark(1);
                    if(!ObjectUtils.isEmpty(taskId)){
                        query.setTaskId(taskId);
                    }
                    List<TaskRecordVO> voList = taskList(query);
                    if(!ObjectUtils.isEmpty(voList)){
                        for (TaskRecordVO vo : voList){
                            vo.setEndTime(hisParent.getEndTime());
                            vo.setAssignee(hisParent.getAssignee());
                            vo.setAssigneeName("");
                            vo.setDuration(hisParent.getDurationInMillis());
                            vo.setRunMark(0);
                            update(vo);
                        }
                    }
                    /********** 更新上一任务 *** end *********/
                    data.add(TaskRecordConvert.INSTANCE.convert(taskRecord));
                    save(taskRecord);
                }
            }else{
                data.add(TaskRecordConvert.INSTANCE.convert(taskRecord));
                save(taskRecord);
            }
        }

        return data;
    }

    @Override
    public TaskRecordEntity getTaskRecordById(Long id) {
        return taskRecordMapper.getTaskRecordById(id);
    }
}