package com.iris.workflow.controller;

import cn.hutool.json.JSONUtil;
import com.iris.framework.common.utils.Result;
import com.iris.workflow.service.ProcessHandlerService;
import com.iris.workflow.service.TaskHandlerService;
import com.iris.workflow.vo.TaskDto;
import com.iris.workflow.vo.TaskVO;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessInstanceWithVariablesImpl;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 任务操作
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-12-19
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskHandlerService taskHandlerService;

    private final ProcessHandlerService processHandlerService;

    public TaskController(TaskHandlerService taskHandlerService, ProcessHandlerService processHandlerService){
        this.taskHandlerService = taskHandlerService;
        this.processHandlerService = processHandlerService;
    }

    /**
     * 根据文件路径，部署流程
     * @param path
     * @return
     */
    @GetMapping("/deploy/{path}")
    @PreAuthorize("hasAuthority('flow:saveOrUpdate')")
    public Result<String> deploy(@PathVariable String path){
        return Result.ok(processHandlerService.getProcessDefIdByDeploy(path, "流程"+System.currentTimeMillis()));
    }

    /**
     * 根据自定义流程key部署流程
     * @param key
     * @return
     */
    @GetMapping("/deployByKey/{key}")
    @PreAuthorize("hasAuthority('flow:saveOrUpdate')")
    public Result<String> deployByKey(@PathVariable String key){
        return Result.ok(processHandlerService.deployByKey(key));
    }

    @GetMapping("/getProcessByKey/{key}")
    public Result<String> getProcessByKey(@PathVariable String key){
        ProcessDefinition processDefinition = this.processHandlerService.getProcessByKey(key);
        return Result.ok(processDefinition.getId());
    }

    @GetMapping("/startFlow/{processId}")
    public Result<String> startFlow(@PathVariable String processId){
        ProcessInstance processInstance = taskHandlerService.startFlow(processId);
        System.out.println(JSONUtil.toJsonStr(processInstance));
        return Result.ok(processInstance.getId());
    }

    @GetMapping("/startByProcessKey/{processKey}")
    public Result<TaskDto> startByProcessKey(@PathVariable String processKey){
        return Result.ok(taskHandlerService.startByProcessKey(processKey));
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
    public Result<String> completeTask(@RequestBody TaskVO taskVO){
        return Result.ok(taskHandlerService.completeTask(taskVO));
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


    @GetMapping("/foo/{processKey}")
    public Result<String> foo(@PathVariable String processKey) throws IOException {
        processHandlerService.test(processKey);
        return Result.ok();
    }
}