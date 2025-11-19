package com.finn.flow.controller;

import com.finn.flow.convert.TaskRecordConvert;
import com.finn.flow.entity.TaskRecordEntity;
import com.finn.flow.service.TaskRecordService;
import com.finn.flow.vo.TaskRecordVO;
import com.finn.core.utils.Result;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 环节运行记录表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-02-06
*/
@RestController
@RequestMapping("workflow/record")
public class TaskRecordController {
    private final TaskRecordService taskRecordService;

    public TaskRecordController(TaskRecordService taskRecordService) {
        this.taskRecordService = taskRecordService;
    }

//    @GetMapping("page")
//    @Operation(summary = "分页")
//    @PreAuthorize("hasAuthority('workflow:record:page')")
//    public Result<PageResult<TaskRecordVO>> page(@ParameterObject @Valid TaskRecordQuery query){
//        PageResult<TaskRecordVO> page = taskRecordService.page(query);
//
//        return Result.ok(page);
//    }

    /**
     * 根据ID获取环节运行记录
     * @param id 流程ID
     * @return
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('workflow:record:info')")
    public Result<TaskRecordVO> get(@PathVariable("id") Long id){
        TaskRecordEntity entity = taskRecordService.getTaskRecordById(id);

        return Result.ok(TaskRecordConvert.INSTANCE.convert(entity));
    }

//    @PostMapping
//    @Operation(summary = "保存")
//    @PreAuthorize("hasAuthority('workflow:record:save')")
//    public Result<String> save(@RequestBody TaskRecordVO vo){
//        taskRecordService.save(vo);
//
//        return Result.ok();
//    }

    /**
     * 修改环节
     * @param vo
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('workflow:record:update')")
    public Result<String> update(@RequestBody @Valid TaskRecordVO vo){
        taskRecordService.update(vo);

        return Result.ok();
    }

    /**
     * 删除环节
     * @param idList
     * @return
     */
    @PostMapping("/del")
    @PreAuthorize("hasAuthority('workflow:record:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        taskRecordService.delete(idList);
        return Result.ok();
    }
}