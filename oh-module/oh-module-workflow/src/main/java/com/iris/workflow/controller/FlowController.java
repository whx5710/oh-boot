package com.iris.workflow.controller;

import com.iris.workflow.convert.FlowConvert;
import com.iris.workflow.entity.FlowEntity;
import com.iris.workflow.query.FlowQuery;
import com.iris.workflow.service.FlowService;
import com.iris.workflow.vo.FlowVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.common.utils.Result;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 自定义流程表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-12-19
*/
@RestController
@RequestMapping("/flow")
@Tag(name="自定义流程表")
public class FlowController {
    private final FlowService flowService;

    public FlowController(FlowService flowService){
        this.flowService = flowService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('flow:page')")
    public Result<PageResult<FlowVO>> page(@ParameterObject @Valid FlowQuery query){
        PageResult<FlowVO> page = flowService.page(query);
        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('iris:flow:info')")
    public Result<FlowVO> get(@PathVariable("id") Long id){
        FlowEntity entity = flowService.getById(id);

        return Result.ok(FlowConvert.INSTANCE.convert(entity));
    }

    @PostMapping("/saveOrUpdate")
    @Operation(summary = "根据keyCode保存或更新")
//    @PreAuthorize("hasAuthority('iris:flow:saveOrUpdate')")
    public Result<String> saveOrUpdate(@RequestBody FlowVO vo){
        flowService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('iris:flow:update')")
    public Result<String> update(@RequestBody @Valid FlowVO vo){
        flowService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('iris:flow:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        flowService.delete(idList);

        return Result.ok();
    }
}