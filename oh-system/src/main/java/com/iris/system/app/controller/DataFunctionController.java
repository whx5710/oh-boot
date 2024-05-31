package com.iris.system.app.controller;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.common.utils.Result;
import com.iris.system.app.convert.DataFunctionConvert;
import com.iris.system.app.entity.DataFunctionEntity;
import com.iris.system.app.query.DataFunctionQuery;
import com.iris.system.app.service.DataFunctionService;
import com.iris.system.app.vo.DataFunctionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 功能列表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-30
*/
@RestController
@RequestMapping("/sys/function")
@Tag(name="功能列表")
public class DataFunctionController {
    @Resource
    private DataFunctionService dataFunctionService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:function:page')")
    public Result<PageResult<DataFunctionVO>> page(@ParameterObject @Valid DataFunctionQuery query){
        PageResult<DataFunctionVO> page = dataFunctionService.page(query);
        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:function:info')")
    public Result<DataFunctionVO> get(@PathVariable("id") Long id){
        DataFunctionEntity entity = dataFunctionService.getById(id);

        return Result.ok(DataFunctionConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('sys:function:save')")
    public Result<String> save(@RequestBody DataFunctionVO vo){
        dataFunctionService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('sys:function:update')")
    public Result<String> update(@RequestBody @Valid DataFunctionVO vo){
        dataFunctionService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('sys:function:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        dataFunctionService.delete(idList);

        return Result.ok();
    }

    @GetMapping("pageByClientId")
    @Operation(summary = "根据客户端获取分页数据")
    @PreAuthorize("hasAuthority('sys:function:page')")
    public Result<PageResult<DataFunctionVO>> pageByClientId(@ParameterObject @Valid DataFunctionQuery query){
        PageResult<DataFunctionVO> page = dataFunctionService.pageByClientId(query);
        return Result.ok(page);
    }
}