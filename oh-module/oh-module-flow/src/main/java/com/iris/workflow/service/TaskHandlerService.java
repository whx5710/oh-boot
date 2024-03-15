package com.iris.workflow.service;

import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.utils.AssertUtils;
import com.iris.framework.security.user.SecurityUser;
import com.iris.workflow.query.TaskRecordQuery;
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

import java.util.*;

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
     * @param processKey 流程定义编码
     * @return
     */
    public List<TaskRecordVO> startByProcessKey(String processKey){
        return startByProcessKey(processKey, null, null);
    }

    /**
     * 启动流程
     * @param processKey 流程定义编码
     * @param params 参数
     * @return
     */
    public List<TaskRecordVO> startByProcessKey(String processKey, Map<String,Object> params){
        return startByProcessKey(processKey, null, params);
    }

    /**
     * 启动流程
     * @param processKey 流程定义编码
     * @param businessKey 业务ID
     * @return
     */
    public List<TaskRecordVO> startByProcessKey(String processKey, String businessKey){
        return startByProcessKey(processKey, businessKey, null);
    }

    /**
     * 启动流程
     * @param processKey 流程定义编码
     * @param businessKey 业务ID
     * @param params 参数
     * @return
     */
    public List<TaskRecordVO> startByProcessKey(String processKey, String businessKey, Map<String,Object> params){
        AssertUtils.isBlank(processKey, "流程定义编码");
        ProcessInstanceWithVariablesImpl processInstance;
        try {
            if(ObjectUtils.isEmpty(businessKey) && ObjectUtils.isEmpty(params)){
                processInstance = (ProcessInstanceWithVariablesImpl) runtimeService.startProcessInstanceByKey(processKey);
            }else if(ObjectUtils.isEmpty(businessKey) && !ObjectUtils.isEmpty(params)){
                processInstance = (ProcessInstanceWithVariablesImpl) runtimeService.startProcessInstanceByKey(processKey, params);
            }else if(!ObjectUtils.isEmpty(businessKey) && ObjectUtils.isEmpty(params)){
                processInstance = (ProcessInstanceWithVariablesImpl) runtimeService.startProcessInstanceByKey(processKey, businessKey);
            }else{
                processInstance = (ProcessInstanceWithVariablesImpl) runtimeService.startProcessInstanceByKey(processKey, businessKey, params);
            }
            // 正在运行的任务
            List<HistoricTaskInstance> list = getTaskInstByProInsId(processInstance.getProcessInstanceId());
            if(!ObjectUtils.isEmpty(list)){
                for(int i = 0; i < list.size(); i++){
                    HistoricTaskInstance his = list.get(i);
                    taskRecordService.saveTaskRecord(processInstance.getProcessInstanceId(), his.getId());
                }
            }
            TaskRecordQuery query = new TaskRecordQuery();
            query.setRunMark(1);
            query.setProcInstId(processInstance.getProcessInstanceId());
            return taskRecordService.taskList(query);
        }catch (NotFoundException e1){
            throw new ServerException("根据流程KEY未找到对应的流程，启动流程失败，请确认是否部署！", e1.getMessage());
        }catch (NullValueException e2){
            throw new ServerException("根据流程KEY未找到对应的流程，启动流程失败，请确认是否部署。", e2.getMessage());
        }
    }

    /**
     * 完成任务
     * 完成任务前，先保存前后任务信息
     * @param taskVO
     */
    public List<TaskRecordVO> completeTask(TaskVO taskVO){
        AssertUtils.isBlank(taskVO.getTaskId(), "【参数异常】任务ID");
        AssertUtils.isBlank(taskVO.getProInstId(), "【参数异常】流程实例ID");
        // 根据流程实例ID获取流程列表，如果为空，说明无待完成的流程
        List<Task> list = getTaskByProInsId(taskVO.getProInstId());
        if(ObjectUtils.isEmpty(list)){
            throw new ServerException("没有找到待操作的流程，请检查！");
        }
        try {
            // 完成任务
            taskService.complete(taskVO.getTaskId(), taskVO.getParams());
            // 保存环节
            taskRecordService.saveTaskRecord(taskVO.getProInstId(), taskVO.getTaskId());
        }catch (NullValueException e1){
            throw new ServerException("任务ID错误，环节未完成！【" + taskVO.getTaskId() + "】");
        }catch (ProcessEngineException e2){
            throw new ServerException("完成环节发生异常，请联系管理员！", e2.getMessage());
        }
        TaskRecordQuery query = new TaskRecordQuery();
        query.setRunMark(1);
        query.setProcInstId(taskVO.getProInstId());
        return taskRecordService.taskList(query);
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
     * 根据流程实例ID获取待办任务(正在运行的)
     * @param proInsId
     * @return
     */
    public List<HistoricTaskInstance> getTaskInstByProInsId(String proInsId){
        List<Task> list = taskService.createTaskQuery().processInstanceId(proInsId).orderByTaskCreateTime().desc().list();
        List<HistoricTaskInstance> taskInstanceList = new ArrayList<>();
        if(!ObjectUtils.isEmpty(list)){
            for(Task task: list){
                taskInstanceList.add(historyService.createHistoricTaskInstanceQuery().processInstanceId(proInsId).taskId(task.getId()).singleResult());
            }
        }
        return taskInstanceList;
    }

    /**
     * 根据流程实例ID获取历史任务
     * @param proInsId
     * @return
     */
    public List<HistoricTaskInstance> getHisTaskInstByProInsId(String proInsId){
        return historyService.createHistoricTaskInstanceQuery().processInstanceId(proInsId)
                .orderByHistoricActivityInstanceStartTime().asc().orderByHistoricTaskInstanceEndTime().asc().list();
    }

    /**
     * 高亮已执行的环节
     * @param proInsId
     */
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