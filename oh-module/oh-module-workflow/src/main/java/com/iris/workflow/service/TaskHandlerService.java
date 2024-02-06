package com.iris.workflow.service;

import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.utils.AssertUtils;
import com.iris.framework.security.user.SecurityUser;
import com.iris.workflow.entity.TaskRecordEntity;
import com.iris.workflow.vo.TaskRecordVO;
import com.iris.workflow.vo.TaskVO;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.exception.NotFoundException;
import org.camunda.bpm.engine.exception.NullValueException;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessInstanceWithVariablesImpl;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 任务相关操作
 * 2023-12-27
 * @author 王小费 whx5710@qq.com
 */
@Service
public class TaskHandlerService {

    private final Logger log = LoggerFactory.getLogger(TaskHandlerService.class);
    private final RuntimeService runtimeService;

    private final TaskService taskService;

    private final TaskRecordService taskRecordService;

    private final HistoryService historyService;

    public TaskHandlerService(RuntimeService runtimeService, TaskService taskService, TaskRecordService taskRecordService,
                              HistoryService historyService){
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.taskRecordService = taskRecordService;
        this.historyService = historyService;
    }

    /**
     * 启动流程
     */
    public ProcessInstance startFlow(String processId){
        try{
            return runtimeService.startProcessInstanceById(processId);
        }catch (NotFoundException e1){
            throw new ServerException("根据流程ID未找到对应的流程，启动流程失败！");
        }
    }

    /**
     * 启动流程
     */
    public List<TaskRecordVO> startByProcessKey(String processKey){
        try{
            ProcessInstanceWithVariablesImpl processInstance = (ProcessInstanceWithVariablesImpl) runtimeService.startProcessInstanceByKey(processKey);
            List<HistoricTaskInstance> list = getHisTaskByProInsId(processInstance.getProcessInstanceId());
            if(!ObjectUtils.isEmpty(list)){
                String fromActInstId = ""; // 来自于环节实例ID
                String fromTaskId = ""; // 来自于任务ID
                String fromTaskDefId = ""; // 来自于任务key
                String fromTaskName = ""; // 环节名称
                for(int i = 0; i < list.size(); i++){
                    HistoricTaskInstance his = list.get(i);
                    saveTaskRecord(processInstance.getProcessInstanceId(), his.getId());
                }
            }
            return taskRecordService.activityTask(processInstance.getProcessInstanceId());
        }catch (NotFoundException e1){
            throw new ServerException("根据流程KEY未找到对应的流程，启动流程失败，请确认是否部署！", e1.getMessage());
        }catch (NullValueException e2){
            throw new ServerException("根据流程KEY未找到对应的流程，启动流程失败，请确认是否部署。", e2.getMessage());
        }
    }

    /**
     * 完成任务
     * @param taskId
     * @param map
     */
    public void completeTask(String taskId, Map<String, Object> map){
        try {
            taskService.complete(taskId, map);
        }catch (NullValueException e1){
            throw new ServerException("任务ID错误，环节未完成！【" + taskId + "】");
        }catch (ProcessEngineException e2){
            throw new ServerException("完成环节发生异常，请联系管理员！", e2.getMessage());
        }
    }

    /**
     * 完成任务
     * 完成任务前，先保存前后任务信息
     * @param taskVO
     */
    public List<TaskRecordVO> completeTask(TaskVO taskVO){
        AssertUtils.isBlank(taskVO.getTaskId(), "【参数异常】任务ID");
        AssertUtils.isBlank(taskVO.getProcInstId(), "【参数异常】流程实例ID");
        // 根据流程实例ID获取流程列表，如果为空，说明无待完成的流程
        List<Task> list = getTaskByProInsId(taskVO.getProcInstId());
        if(ObjectUtils.isEmpty(list)){
            throw new ServerException("没有找到待操作的流程，请检查！");
        }
        try {
            // 完成任务
            taskService.complete(taskVO.getTaskId(), taskVO.getParams());
            // 保存环节
            saveTaskRecord(taskVO.getProcInstId(), taskVO.getTaskId());
        }catch (NullValueException e1){
            throw new ServerException("任务ID错误，环节未完成！【" + taskVO.getTaskId() + "】");
        }catch (ProcessEngineException e2){
            throw new ServerException("完成环节发生异常，请联系管理员！", e2.getMessage());
        }
        return taskRecordService.activityTask(taskVO.getProcInstId());
    }

    /**
     * 根据流程实例ID获取待办任务(正在运行的)
     * @param proInsId
     * @return
     */
    public List<Task> getTaskByProInsId(String proInsId){
        return taskService.createTaskQuery().processInstanceId(proInsId).orderByTaskCreateTime().desc().list();
    }

    /**
     * 根据流程实例ID获取历史任务
     * @param proInsId
     * @return
     */
    public List<HistoricTaskInstance> getHisTaskByProInsId(String proInsId){
        return historyService.createHistoricTaskInstanceQuery().processInstanceId(proInsId)
                .orderByHistoricActivityInstanceStartTime().asc().orderByHistoricTaskInstanceEndTime().asc().list();
    }

    /**
     * 保存 环节运行记录表
     * @param proInsId
     * @param taskId
     */
    public void saveTaskRecord(String proInsId, String taskId){
        AssertUtils.isBlank(proInsId, "【参数异常】流程实例ID");
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(proInsId).unfinished()
                .orderByHistoricActivityInstanceStartTime().asc()
                .orderByHistoricTaskInstanceEndTime().asc().list();
        if(!ObjectUtils.isEmpty(list)){
            // 修改当前运行标志,全部改为已完成
            /******************************************************************************
             *
             * 不能批量更新运行状态，需直接找到上一个运行状态进行更新，还行更新结束时间，时长，受理人等
             *
            /****************************************************************************/
            taskRecordService.updateRunMark(proInsId);
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
                    List<HistoricTaskInstance> parentList = historyService.createHistoricTaskInstanceQuery()
                            .processInstanceId(proInsId).taskId(taskId).orderByHistoricActivityInstanceStartTime().desc()
                            .orderByHistoricTaskInstanceEndTime().desc().list();
                    if(!ObjectUtils.isEmpty(parentList)){
                        for(HistoricTaskInstance hisParent: parentList){
                            taskRecord.setFromActInstId(hisParent.getActivityInstanceId());
                            taskRecord.setFromTaskDefId(hisParent.getTaskDefinitionKey());
                            taskRecord.setFromTaskId(hisParent.getId());
                            taskRecord.setFromTaskName(hisParent.getName());
                            taskRecordService.save(taskRecord);
                        }
                    }else{
                        taskRecordService.save(taskRecord);
                    }
                }else{
                    taskRecordService.save(taskRecord);
                }
            }
        }
    }


    public void getHighlightNode(String proInsId){
        HistoricProcessInstance hisProIns = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(proInsId).singleResult();
        System.out.println(hisProIns.getProcessDefinitionName()+" "+hisProIns.getProcessDefinitionKey());
        //===================已完成节点
        List<HistoricActivityInstance> finished = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(proInsId)
                .finished()
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
        Set<String> highPoint = new HashSet<>();
        finished.forEach(t -> highPoint.add(t.getActivityId()));

        //=================待完成节点
        List<HistoricActivityInstance> unfinished = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(proInsId).unfinished().list();
        Set<String> waitingToDo = new HashSet<>();
        unfinished.forEach(t -> waitingToDo.add(t.getActivityId()));

        //=================iDo 我执行过的
        Set<String> iDo = new HashSet<>(); //存放 高亮 我的办理节点
        List<HistoricTaskInstance> taskInstanceList = historyService.createHistoricTaskInstanceQuery().taskAssignee(SecurityUser.getUser().getUsername()).finished().processInstanceId(proInsId).list();
        taskInstanceList.forEach(a -> iDo.add(a.getTaskDefinitionKey()));
    }

}