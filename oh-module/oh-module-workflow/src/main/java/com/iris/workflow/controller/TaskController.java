package com.iris.workflow.controller;

import cn.hutool.json.JSONUtil;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.utils.Result;
import com.iris.workflow.service.ProcessHandlerService;
import com.iris.workflow.service.TaskHandlerService;
import jakarta.annotation.Resource;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


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
    private Result<String> startByProcessKey(@PathVariable String processKey){
        ProcessInstance processInstance = taskHandlerService.startByProcessKey(processKey);
        return Result.ok(processInstance.getId());
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
    @PostMapping("/completeTask")
    public String completeTask(@RequestBody HashMap map){
        if(!map.containsKey("taskId") || map.get("taskId") == null){
            throw new ServerException("参数异常,【taskId】不能为空.");
        }
        String taskId = (String) map.get("taskId");
        if(taskId.equals("")){
            throw new ServerException("参数异常,【taskId】不能为空!");
        }
        taskHandlerService.completeTask(taskId, map);
        return "ok";
    }

    @GetMapping("/getTaskByProInsId/{proInsId}")
    public Result<String> getTaskByProInsId(String proInsId){
        List<Task> list = taskHandlerService.getTaskByProInsId(proInsId);
        if(list != null){
            for(Task task: list){
                System.out.println(task.getName());
            }
        }
        return Result.ok("");
    }
}