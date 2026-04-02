package com.finn.flow.service.flowable;

import com.finn.flow.service.TaskRecordService;
import com.finn.framework.utils.AssertUtils;
import com.finn.flow.vo.TaskRecordVO;
import com.finn.flow.vo.TaskVO;
import com.finn.framework.exception.ServerException;
import com.finn.framework.security.user.SecurityUser;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(rollbackFor = Exception.class)
    public List<TaskRecordVO> startByProcessKey(String processKey, String businessKey, Map<String,Object> params){
        AssertUtils.isBlank(processKey, "流程定义编码");
        ProcessInstance processInstance;

        if(ObjectUtils.isEmpty(businessKey) && ObjectUtils.isEmpty(params)){
            processInstance = runtimeService.startProcessInstanceByKey(processKey);
        }else if(ObjectUtils.isEmpty(businessKey) && !ObjectUtils.isEmpty(params)){
            processInstance = runtimeService.startProcessInstanceByKey(processKey, params);
        }else if(!ObjectUtils.isEmpty(businessKey) && ObjectUtils.isEmpty(params)){
            processInstance = runtimeService.startProcessInstanceByKey(processKey, businessKey);
        }else{
            processInstance = runtimeService.startProcessInstanceByKey(processKey, businessKey, params);
        }
        // 正在运行的任务
        List<HistoricTaskInstance> list = getTaskInstByProInsId(processInstance.getProcessInstanceId());
        if(!ObjectUtils.isEmpty(list)){
            for (HistoricTaskInstance his : list) {
                taskRecordService.saveTaskRecord(processInstance.getProcessInstanceId(), his.getId());
            }
        }
        return taskRecordService.getRunRecord(processInstance.getProcessInstanceId());
    }

    /**
     * 完成任务
     * 完成任务前，先保存前后任务信息
     * @param taskVO
     */
    //@Transactional(rollbackFor = Exception.class)
    public List<TaskRecordVO> completeTask(TaskVO taskVO){
        AssertUtils.isBlank(taskVO.getTaskId(), "【参数异常】任务ID");
        AssertUtils.isBlank(taskVO.getProInstId(), "【参数异常】流程实例ID");
        // 根据流程实例ID获取流程列表，如果为空，说明无待完成的流程
        List<Task> list = getTaskByProInsId(taskVO.getProInstId());
        if(ObjectUtils.isEmpty(list)){
            throw new ServerException("没有找到待操作的流程，请检查！");
        }
        // 完成任务
        taskService.complete(taskVO.getTaskId(), taskVO.getParams());
        // 保存环节
        taskRecordService.saveTaskRecord(taskVO.getProInstId(), taskVO.getTaskId());
        return taskRecordService.getRunRecord(taskVO.getProInstId());
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
                taskInstanceList.add(historyService.createHistoricTaskInstanceQuery().processInstanceId(proInsId)
                        .taskId(task.getId()).singleResult());
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
                .orderByHistoricTaskInstanceStartTime().asc().orderByHistoricTaskInstanceEndTime().asc().list();
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
                .processInstanceId(proInsId).finished()
                .orderByHistoricActivityInstanceStartTime()
                .asc().orderByHistoricActivityInstanceEndTime()
                .asc().list();
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