package com.iris.workflow.service;

import jakarta.annotation.Resource;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;


@Service
public class TaskHandlerService {

    @Resource
    private RepositoryService repositoryService;

    @Resource
    RuntimeService runtimeService;


    @Resource
    TaskService taskService;

    /**
     * 启动流程
     */
    public ProcessInstance startFlow(String processId){
        return runtimeService.startProcessInstanceById(processId);
    }

    /**
     * 完成任务
     * @param taskId
     */
    public void completeTask(String taskId){
        // 根据用户找到关联的Task
//        Task task = taskService.createTaskQuery()
//                //.processInstanceId("eff78817-2e58-11ed-aa3f-c03c59ad2248")
//                .taskAssignee("admin")
//                .singleResult();
//        if(task != null ){
//            taskService.complete(task.getId());
//            System.out.println("任务审批完成...");
//        }
//        List<Task> list = taskService.createTaskQuery().orderByFollowUpDate().desc().list();
//        list.forEach(item -> {
//            System.out.println(item);
//            taskService.complete(item.getId());
//        });
//        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        taskService.complete(taskId);
    }
}