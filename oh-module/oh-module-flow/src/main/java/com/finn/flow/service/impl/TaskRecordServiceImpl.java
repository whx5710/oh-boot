package com.finn.flow.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.finn.core.utils.DateUtils;
import com.finn.flow.convert.TaskRecordConvert;
import com.finn.flow.entity.TaskRecordEntity;
import com.finn.flow.mapper.TaskRecordMapper;
import com.finn.flow.query.TaskRecordQuery;
import com.finn.flow.service.TaskRecordService;
import com.finn.flow.vo.TaskRecordVO;
import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.PageResult;
import com.finn.support.cache.UserCache;
import com.finn.support.entity.UserEntity;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger log = LoggerFactory.getLogger(TaskRecordServiceImpl.class);

    private final HistoryService historyService;

    private final TaskRecordMapper taskRecordMapper;


    private final UserCache userCache;


    public TaskRecordServiceImpl(HistoryService historyService, TaskRecordMapper taskRecordMapper,
                                 UserCache userCache){
        this.historyService = historyService;
        this.taskRecordMapper = taskRecordMapper;
        this.userCache = userCache;
    }

    @Override
    public PageResult<TaskRecordEntity> page(TaskRecordQuery query) {
        Page<TaskRecordEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TaskRecordEntity> list = taskRecordMapper.getTaskList(new TaskRecordQuery());
        return new PageResult<>(list, page.getTotal());
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
        if(!ObjectUtils.isEmpty(list)){ // 未完成的
            for(HistoricTaskInstance his: list){
                TaskRecordEntity taskRecord = new TaskRecordEntity();
                taskRecord.setProcDefId(his.getProcessDefinitionId());
                taskRecord.setProcInstId(his.getProcessInstanceId());
                taskRecord.setActInstId(his.getActivityInstanceId());
                taskRecord.setTaskId(his.getId());
                taskRecord.setTaskDefId(his.getTaskDefinitionKey());
                taskRecord.setTaskName(his.getName());

                // 当前标识，默认0，1标识当前环节
                taskRecord.setRunMark(getRunMark(his));
                // 签收人
                taskRecord.setAssignee(his.getAssignee());
                if(his.getAssignee() != null){
                    String assignee = his.getAssignee();
                    try{
                        Long userId = Long.valueOf(assignee);
                        UserEntity userEntity = userCache.getUser(userId);
                        if(userEntity != null){
                            taskRecord.setAssigneeName(userEntity.getRealName());
                        }
                    }catch (Exception e){
                        log.error("获取用户信息错误！{}", e.getMessage());
                    }
                }
                taskRecord.setStartTime(DateUtils.dateToLocalDate(his.getStartTime()));
                taskRecord.setEndTime(DateUtils.dateToLocalDate(his.getEndTime()));
                taskRecord.setDuration(his.getDurationInMillis());

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
                            updatePreTask(proInsId, taskId, hisParent);
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
            // 已完成
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
                    taskRecord.setStartTime(DateUtils.dateToLocalDate(hisParent.getEndTime()));
                    taskRecord.setEndTime(DateUtils.dateToLocalDate(hisParent.getEndTime()));

                    taskRecord.setProcDefId(hisParent.getProcessDefinitionId());
                    taskRecord.setProcInstId(proInsId);
                    taskRecord.setRunMark(getRunMark(hisParent));
                    /************ 更新上一任务 *** start *********/
                    updatePreTask(proInsId, taskId, hisParent);
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

    /**
     * 获取正在运行的记录
     * @param proInstId
     * @return
     */
    @Override
    public List<TaskRecordVO> getRunRecord(String proInstId){
        TaskRecordQuery query = new TaskRecordQuery();
        query.setRunMark(1);
        query.setProcInstId(proInstId);
        return taskList(query);
    }

    /**
     * 更新上一环节
     * @param proInsId
     * @param taskId
     * @param hisParent
     */
    private void updatePreTask(String proInsId, String taskId, HistoricTaskInstance hisParent){
        TaskRecordQuery query = new TaskRecordQuery();
        query.setProcInstId(proInsId);
        query.setRunMark(1);
        if(!ObjectUtils.isEmpty(taskId)){
            query.setTaskId(taskId);
        }
        List<TaskRecordVO> voList = taskList(query);
        if(!ObjectUtils.isEmpty(voList)){
            for (TaskRecordVO vo : voList){
                vo.setEndTime(DateUtils.dateToLocalDate(hisParent.getEndTime()));
                vo.setAssignee(hisParent.getAssignee());
                vo.setAssigneeName("");
                vo.setDuration(hisParent.getDurationInMillis());
                vo.setRunMark(0);
                update(vo);
            }
        }
    }

    /**
     * 获取是否运行状态
     * @param his
     * @return
     */
    private Integer getRunMark(HistoricTaskInstance his){
        return (his.getEndTime()!=null && his.getDeleteReason()!= null
                && his.getDeleteReason().equalsIgnoreCase("completed"))?0:1;
    }
}