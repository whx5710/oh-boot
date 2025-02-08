package com.finn.flow.controller;

import com.finn.flow.convert.FlowNodeConvert;
import com.finn.flow.entity.FlowNodeEntity;
import com.finn.flow.query.FlowNodeQuery;
import com.finn.flow.service.FlowNodeService;
import com.finn.flow.vo.FlowNodeVO;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 环节定义表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-01-31
*/
@RestController
@RequestMapping("finn/node")
@Tag(name="环节定义表")
public class FlowNodeController {
    
    private final FlowNodeService flowNodeService;

    public FlowNodeController(FlowNodeService flowNodeService){
        this.flowNodeService = flowNodeService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('finn:node:page')")
    public Result<PageResult<FlowNodeVO>> page(@ParameterObject @Valid FlowNodeQuery query){
        PageResult<FlowNodeVO> page = flowNodeService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('finn:node:info')")
    public Result<FlowNodeVO> get(@PathVariable("id") Long id){
        FlowNodeEntity entity = flowNodeService.getById(id);

        return Result.ok(FlowNodeConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('finn:node:save')")
    public Result<String> save(@RequestBody FlowNodeVO vo){
        flowNodeService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('finn:node:update')")
    public Result<String> update(@RequestBody @Valid FlowNodeVO vo){
        flowNodeService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('finn:node:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        flowNodeService.delete(idList);
        return Result.ok();
    }
}