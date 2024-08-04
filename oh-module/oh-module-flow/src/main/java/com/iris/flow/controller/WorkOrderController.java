package com.iris.flow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.common.utils.Result;
import com.iris.flow.convert.WorkOrderConvert;
import com.iris.flow.entity.WorkOrderEntity;
import com.iris.flow.service.WorkOrderService;
import com.iris.flow.query.WorkOrderQuery;
import com.iris.flow.vo.WorkOrderVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 工单表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-02-23
*/
@RestController
@RequestMapping("workflow/order")
@Tag(name="工单表")
public class WorkOrderController {
    private final WorkOrderService workOrderService;

    public WorkOrderController(WorkOrderService workOrderService){
        this.workOrderService = workOrderService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('workflow:order:page')")
    public Result<PageResult<WorkOrderVO>> page(@ParameterObject @Valid WorkOrderQuery query){
        PageResult<WorkOrderVO> page = workOrderService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('workflow:order:info')")
    public Result<WorkOrderVO> get(@PathVariable("id") Long id){
        WorkOrderEntity entity = workOrderService.getOrderById(id);

        return Result.ok(WorkOrderConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('workflow:order:save')")
    public Result<String> save(@RequestBody WorkOrderVO vo){
        workOrderService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('workflow:order:update')")
    public Result<String> update(@RequestBody @Valid WorkOrderVO vo){
        workOrderService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('workflow:order:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        workOrderService.delete(idList);

        return Result.ok();
    }
}