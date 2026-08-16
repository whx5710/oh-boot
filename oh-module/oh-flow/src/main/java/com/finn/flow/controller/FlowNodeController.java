package com.finn.flow.controller;

import com.finn.flow.convert.FlowNodeConvert;
import com.finn.flow.entity.FlowNodeEntity;
import com.finn.flow.query.FlowNodeQuery;
import com.finn.flow.service.FlowNodeService;
import com.finn.flow.vo.FlowNodeVO;
import com.finn.common.entity.PageResult;
import com.finn.common.entity.Result;
import jakarta.validation.Valid;
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
@RequestMapping("/flow/node")
public class FlowNodeController {
    
    private final FlowNodeService flowNodeService;

    public FlowNodeController(FlowNodeService flowNodeService){
        this.flowNodeService = flowNodeService;
    }

    /**
     * 分页
     * @param query 查询条件
     * @return 列表
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('flow:node:page')")
    public Result<PageResult<FlowNodeVO>> page(@Valid FlowNodeQuery query){
        PageResult<FlowNodeVO> page = flowNodeService.page(query);

        return Result.ok(page);
    }

    /**
     * 根据ID获取流程信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('flow:node:info')")
    public Result<FlowNodeVO> get(@PathVariable("id") Long id){
        FlowNodeEntity entity = flowNodeService.getById(id);

        return Result.ok(FlowNodeConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存
     * @param vo 流程信息
     * @return 提示信息
     */
    @PostMapping
    @PreAuthorize("hasAuthority('flow:node:save')")
    public Result<String> save(@RequestBody FlowNodeVO vo){
        flowNodeService.save(vo);

        return Result.ok();
    }

    /**
     * 修改
     * @param vo 流程信息
     * @return 提示信息
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('flow:node:update')")
    public Result<String> update(@RequestBody @Valid FlowNodeVO vo){
        flowNodeService.update(vo);

        return Result.ok();
    }

    /**
     * 修改
     * @param vo 流程信息
     * @return 提示信息
     */
    @PostMapping("/updateBatch")
    @PreAuthorize("hasAuthority('flow:node:update')")
    public Result<String> updateBatch(@RequestBody @Valid List<FlowNodeVO> vo){
        flowNodeService.updateBatch(vo);

        return Result.ok();
    }
    /**
     * 删除
     * @param idList 流程ID集合
     * @return 提示信息
     */
    @PostMapping("/del")
    @PreAuthorize("hasAuthority('flow:node:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        flowNodeService.delete(idList);
        return Result.ok();
    }
}