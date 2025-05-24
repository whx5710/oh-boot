package com.finn.flow.controller;

import com.finn.flow.service.ProcessHandlerService;
import com.finn.flow.service.TaskHandlerService;
import com.finn.flow.vo.TaskRecordVO;
import com.finn.flow.vo.TaskVO;
import com.finn.core.utils.Result;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.Task;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * 根据KEY获取流程ID
     * @param key
     * @return
     */
    @GetMapping("/getProcessByKey/{key}")
    public Result<String> getProcessByKey(@PathVariable String key){
        ProcessDefinition processDefinition = this.processHandlerService.getProcessByKey(key);
        return Result.ok(processDefinition.getId());
    }

    /**
     * 启动流程
     * @param taskVO
     * @return
     */
    @PostMapping("/startFlow")
    public Result<List<TaskRecordVO>> startFlow(@RequestBody TaskVO taskVO){
        return Result.ok(taskHandlerService.startByProcessKey(taskVO.getProDefKey(), taskVO.getBusinessKey(), taskVO.getParams()));
    }

    /**
     * 根据自定义流程key启动流程
     * @param processKey 流程KEY
     * @return
     */
    @GetMapping("/startByProcessKey/{processKey}")
    public Result<List<TaskRecordVO>> startByProcessKey(@PathVariable String processKey){
        return Result.ok(taskHandlerService.startByProcessKey(processKey));
    }

    /**
     * 完成任务
     * @param taskVO
     * @return
     */
    @PostMapping("/completeTask")
    public Result<List<TaskRecordVO>> completeTask(@RequestBody TaskVO taskVO){
        return Result.ok(taskHandlerService.completeTask(taskVO));
    }

    /**
     *
     * @param proInsId
     * @return
     */
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
        taskHandlerService.getHighlightNode(processKey);
        return Result.ok();
    }
}