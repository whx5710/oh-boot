package com.iris.workflow.service;

import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.utils.AssertUtils;
import com.iris.framework.security.user.SecurityUser;
import com.iris.workflow.entity.TaskRunEntity;
import com.iris.workflow.vo.TaskDto;
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
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
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

    private final TaskRunService taskRunService;

    private final HistoryService historyService;

    public TaskHandlerService(RuntimeService runtimeService, TaskService taskService, TaskRunService taskRunService,
                              HistoryService historyService){
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.taskRunService = taskRunService;
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
    public TaskDto startByProcessKey(String processKey){
        try{
            ProcessInstanceWithVariablesImpl processInstance = (ProcessInstanceWithVariablesImpl) runtimeService.startProcessInstanceByKey(processKey);
            TaskDto taskDto = new TaskDto();
            taskDto.setProcDefId(processInstance.getProcessDefinitionId());
            taskDto.setProcInstId(processInstance.getProcessInstanceId());
            List<Task> list = getTaskByProInsId(processInstance.getProcessInstanceId());
            if(!ObjectUtils.isEmpty(list)){
                Task task = list.get(0);
                taskDto.setTaskId(task.getId());
                // 保存环节记录
                TaskRunEntity taskRun = new TaskRunEntity();
                taskRun.setProcDefId(task.getProcessDefinitionId());
                taskRun.setProcInstId(task.getProcessInstanceId());
                taskRun.setTaskId(task.getId());
                taskRun.setActDefId(task.getTaskDefinitionKey());
                taskRun.setNodeName(task.getName());
                taskRun.setRunMark(1); // 当前标识，默认0，1标识当前环节
                taskRunService.save(taskRun);
            }
            ExecutionEntity entity = processInstance.getExecutionEntity();
            taskDto.setNodeName(entity.getCurrentActivityName());
            taskDto.setTaskDefKey(entity.getActivityId());
            return taskDto;
        }catch (NotFoundException e1){
            throw new ServerException("根据流程KEY未找到对应的流程，启动流程失败！", e1.getMessage());
        }catch (NullValueException e2){
            throw new ServerException("根据流程KEY未找到对应的流程，启动流程失败！", e2.getMessage());
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
    public TaskDto completeTask(TaskVO taskVO){
        AssertUtils.isBlank(taskVO.getTaskId(), "【参数异常】任务ID");
        AssertUtils.isBlank(taskVO.getProcInstId(), "【参数异常】流程实例ID");
        // 根据流程实例ID获取流程列表，如果为空，说明无待完成的流程
        List<Task> list = getTaskByProInsId(taskVO.getProcInstId());
        if(ObjectUtils.isEmpty(list)){
            throw new ServerException("没有找到待操作的流程，请检查！");
        }

        TaskDto dto = new TaskDto();
        TaskRunEntity taskRun = new TaskRunEntity();
        taskRun.setProcInstId(taskVO.getProcInstId());

        Task task = list.get(0);
        taskRun.setFromTaskId(task.getId());
        taskRun.setFromActDefId(task.getTaskDefinitionKey());
        taskRun.setFromNodeName(task.getName());
        taskRun.setProcDefId(task.getProcessDefinitionId());
        try {
            // 完成任务
            taskService.complete(taskVO.getTaskId(), taskVO.getParams());
            // 获取最新的任务
            list = getTaskByProInsId(taskVO.getProcInstId());
            taskRunService.updateRunMark(taskVO.getProcInstId()); // 修改当前环节标识
            if(!ObjectUtils.isEmpty(list)){
                task = list.get(0);
                taskRun.setTaskId(task.getId());
                taskRun.setActDefId(task.getTaskDefinitionKey());
                taskRun.setNodeName(task.getName());
                taskRun.setRunMark(1);

                dto.setTaskId(taskRun.getTaskId());
                dto.setNodeName(taskRun.getNodeName());
                dto.setProcInstId(taskRun.getProcInstId());
                dto.setProcDefId(taskRun.getProcDefId());
                dto.setTaskDefKey(taskRun.getActDefId());
            }
            taskRunService.save(taskRun); // 保存
        }catch (NullValueException e1){
            throw new ServerException("任务ID错误，环节未完成！【" + taskVO.getTaskId() + "】");
        }catch (ProcessEngineException e2){
            throw new ServerException("完成环节发生异常，请联系管理员！", e2.getMessage());
        }
        return dto;
    }

    /**
     * 根据流程实例ID获取待办任务(正在运行的)
     * @param proInsId
     * @return
     */
    public List<Task> getTaskByProInsId(String proInsId){
        return taskService.createTaskQuery().processInstanceId(proInsId).orderByTaskCreateTime().desc().list();
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