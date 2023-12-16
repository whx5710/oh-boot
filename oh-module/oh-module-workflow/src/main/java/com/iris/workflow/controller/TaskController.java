package com.iris.workflow.controller;

import cn.hutool.json.JSONUtil;
import com.iris.framework.common.utils.Result;
import com.iris.workflow.service.ProcessHandlerService;
import com.iris.workflow.service.TaskHandlerService;
import jakarta.annotation.Resource;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/task")
public class TaskController {

    @Resource
    TaskHandlerService taskHandlerService;

    @Resource
    ProcessHandlerService processHandlerService;

    /**
     * 部署流程
     * @param path
     * @return
     */
    @GetMapping("/deploy/{path}")
    public Result<String> deplopy(@PathVariable String path){
        return Result.ok(processHandlerService.getProcessDefIdByDeploy(path, "流程"+System.currentTimeMillis()));
    }

    @GetMapping("/getProcessByKey/{key}")
    public Result<String> getProcessByKey(@PathVariable String key){
        ProcessDefinition processDefinition = processHandlerService.getProcessByKey(key);
        return Result.ok(processDefinition.getId());
    }

    @GetMapping("/startFlow/{processId}")
    private Result<String> startFlow(@PathVariable String processId){
        ProcessInstance processInstance = taskHandlerService.startFlow(processId);
        System.out.println(JSONUtil.toJsonStr(processInstance));
        return Result.ok(processInstance.getId());
    }

    @GetMapping("/startByProcessKey/{processKey}")
    private String startByProcessKey(@PathVariable String processKey){
        ProcessInstance processInstance = taskHandlerService.startByProcessKey(processKey);
        return "启动流程";
    }

    /**
     * 查询任务
     *    待办
     *
     *  流程定义ID:processDefinition : 我们部署流程的时候会，每一个流程都会产生一个流程定义ID
     *  流程实例ID:processInstance ：我们启动流程实例的时候，会产生一个流程实例ID
     */
    @GetMapping("/queryTask")
    public void queryTask(){
//        List<Task> list = taskService.createTaskQuery()
//                //.processInstanceId("eff78817-2e58-11ed-aa3f-c03c59ad2248")
//                .taskAssignee("admin")
//                .list();
//        if(list != null && list.size() > 0){
//            for (Task task : list) {
//                System.out.println("task.getId() = " + task.getId());
//                System.out.println("task.getAssignee() = " + task.getAssignee());
//            }
//        }
    }

    /**
     * 完成任务
     */
    @GetMapping("/completeTask/{taskId}")
    public String completeTask(@PathVariable String taskId){
        Map<String, Object> map = new HashMap<>();
        map.put("money", 99);
        taskHandlerService.completeTask(taskId, map);
        return "ok";
    }
}