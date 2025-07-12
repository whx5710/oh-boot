package com.finn.flow.controller;

import com.finn.flow.convert.WorkOrderConvert;
import com.finn.flow.entity.WorkOrderEntity;
import com.finn.flow.query.WorkOrderQuery;
import com.finn.flow.service.WorkOrderService;
import com.finn.flow.vo.WorkOrderVO;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 工单表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-02-23
*/
@RestController
@RequestMapping("workflow/order")
public class WorkOrderController {
    private final WorkOrderService workOrderService;

    public WorkOrderController(WorkOrderService workOrderService){
        this.workOrderService = workOrderService;
    }

    /**
     * 分页
     * @param query 分页查询
     * @return
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('workflow:order:page')")
    public Result<PageResult<WorkOrderVO>> page(@Valid WorkOrderQuery query){
        PageResult<WorkOrderVO> page = workOrderService.page(query);
        return Result.ok(page);
    }

    /**
     * 根据ID获取工单
     * @param id 工单ID
     * @return 工单信息
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('workflow:order:info')")
    public Result<WorkOrderVO> get(@PathVariable("id") Long id){
        WorkOrderEntity entity = workOrderService.getOrderById(id);
        return Result.ok(WorkOrderConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存工单
     * @param vo
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('workflow:order:save')")
    public Result<String> save(@RequestBody WorkOrderVO vo){
        workOrderService.save(vo);
        return Result.ok();
    }

    /**
     * 修改工单
     * @param vo
     * @return
     */
    @PutMapping
    @PreAuthorize("hasAuthority('workflow:order:update')")
    public Result<String> update(@RequestBody @Valid WorkOrderVO vo){
        workOrderService.update(vo);
        return Result.ok();
    }

    /**
     * 删除工单
     * @param idList
     * @return
     */
    @DeleteMapping
    @PreAuthorize("hasAuthority('workflow:order:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        workOrderService.delete(idList);
        return Result.ok();
    }
}