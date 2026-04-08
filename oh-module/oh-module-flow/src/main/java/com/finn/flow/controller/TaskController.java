package com.finn.flow.controller;

import com.finn.flow.service.flowable.ProcessHandlerService;
import com.finn.flow.service.flowable.TaskHandlerService;
import com.finn.flow.vo.FlowNodeVO;
import com.finn.flow.vo.TaskRecordVO;
import com.finn.flow.vo.TaskVO;
import com.finn.framework.entity.Result;
import com.finn.framework.exception.ServerException;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasAuthority('flow:task:saveOrUpdate')")
    public Result<String> deploy(@PathVariable String path){
        return Result.ok(processHandlerService.getProcessDefIdByDeploy(path, "流程"+System.currentTimeMillis()));
    }

    /**
     * 根据自定义流程key部署流程
     * @param key
     * @return
     */
    @GetMapping("/deployByKey/{key}")
    @PreAuthorize("hasAuthority('flow:task:saveOrUpdate')")
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
        if(processDefinition == null){
            throw new ServerException("未找到对应的流程");
        }
        return Result.ok(processDefinition.getId());
    }

    /**
     * 启动流程
     * @param taskVO
     * @return
     */
    @PostMapping("/startFlow")
    @PreAuthorize("hasAuthority('flow:task:start')")
    public Result<List<TaskRecordVO>> startFlow(@RequestBody TaskVO taskVO){
        return Result.ok(taskHandlerService.startByProcessId(taskVO.getProDefId(), taskVO.getBusinessKey(), taskVO.getParams()));
    }

    /**
     * 根据自定义流程key启动流程
     * @param procDefId 流程ID
     * @return
     */
    @GetMapping("/startByProcessId/{procDefId}")
    @PreAuthorize("hasAuthority('flow:task:start')")
    public Result<List<TaskRecordVO>> startByProcessId(@PathVariable String procDefId){
        return Result.ok(taskHandlerService.startByProcessId(procDefId));
    }

    /**
     * 完成任务
     * @param taskVO
     * @return
     */
    @PostMapping("/completeTask")
    @PreAuthorize("hasAuthority('flow:task:complete')")
    public Result<List<TaskRecordVO>> completeTask(@RequestBody TaskVO taskVO){
        return Result.ok(taskHandlerService.completeTask(taskVO));
    }

    /**
     * 驳回任务 - 退回到上一个任务节点
     * @param taskVO
     * @return
     */
    @PostMapping("/rollbackToPreviousNode")
    @PreAuthorize("hasAuthority('flow:task:rollback')")
    public Result<List<TaskRecordVO>> rollbackToPreviousNode(@RequestBody TaskVO taskVO){
        return Result.ok(taskHandlerService.rollbackToPreviousNode(taskVO));
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

    /**
     * 获取当前任务的下一环节节点
     * @param taskId t
     * @return r
     */
    @GetMapping("/getNextNodes/{taskId}")
    @PreAuthorize("hasAuthority('flow:task:info')")
    public Result<List<FlowNodeVO>> getNextNodes(@PathVariable String taskId) {
        return Result.ok(taskHandlerService.getNextNodes(taskId));
    }
}