package com.iris.workflow.service;

import com.iris.framework.common.exception.ServerException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.exception.NotFoundException;
import org.camunda.bpm.engine.exception.NullValueException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TaskHandlerService {
    private final RuntimeService runtimeService;

    private final TaskService taskService;

    public TaskHandlerService(RuntimeService runtimeService, TaskService taskService){
        this.runtimeService = runtimeService;
        this.taskService = taskService;
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
    public ProcessInstance startByProcessKey(String processKey){
        return runtimeService.startProcessInstanceByKey(processKey);
    }

    /**
     * 完成任务
     * @param taskId
     */
    public void completeTask(String taskId, Map<String, Object> map){
        try {
            taskService.complete(taskId, map);
        }catch (NullValueException e1){
            throw new ServerException("任务ID错误，环节未完成！【" + taskId + "】");
        }
    }

}