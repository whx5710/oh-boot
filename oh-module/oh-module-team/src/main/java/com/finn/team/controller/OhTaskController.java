package com.finn.team.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.team.convert.OhTaskConvert;
import com.finn.team.entity.OhTaskEntity;
import com.finn.team.query.OhTaskQuery;
import com.finn.team.service.OhTaskService;
import com.finn.team.vo.OhTaskVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 任务表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@RestController
@RequestMapping("team/task")
@Tag(name="任务表")
public class OhTaskController {
    private final OhTaskService ohTaskService;

    public OhTaskController(OhTaskService ohTaskService) {
        this.ohTaskService = ohTaskService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('team:task:page')")
    public Result<PageResult<OhTaskVO>> page(@Valid OhTaskQuery query){
        PageResult<OhTaskVO> page = ohTaskService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('team:task:info')")
    public Result<OhTaskVO> get(@PathVariable("id") Long id){
        OhTaskEntity entity = ohTaskService.getById(id);

        return Result.ok(OhTaskConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('team:task:save')")
    public Result<String> save(@RequestBody OhTaskVO vo){
        ohTaskService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('team:task:update')")
    public Result<String> update(@RequestBody @Valid OhTaskVO vo){
        ohTaskService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('team:task:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        ohTaskService.delete(idList);

        return Result.ok();
    }
}