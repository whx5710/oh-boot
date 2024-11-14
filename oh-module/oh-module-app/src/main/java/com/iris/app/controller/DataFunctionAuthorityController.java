package com.iris.app.controller;

import com.iris.app.convert.DataFunctionAuthorityConvert;
import com.iris.app.entity.DataFunctionAuthorityEntity;
import com.iris.app.query.DataFunctionAuthorityQuery;
import com.iris.app.service.DataFunctionAuthorityService;
import com.iris.app.vo.DataFunctionAuthorityVO;
import com.iris.core.utils.PageResult;
import com.iris.core.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 客户端接口授权
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-29
*/
@RestController
@RequestMapping("/sys/authority")
@Tag(name="客户端接口授权")
public class DataFunctionAuthorityController {

    private final DataFunctionAuthorityService dataFunctionAuthorityService;

    public DataFunctionAuthorityController(DataFunctionAuthorityService dataFunctionAuthorityService) {
        this.dataFunctionAuthorityService = dataFunctionAuthorityService;
    }


    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:authority:page')")
    public Result<PageResult<DataFunctionAuthorityVO>> page(@ParameterObject @Valid DataFunctionAuthorityQuery query){
        PageResult<DataFunctionAuthorityVO> page = dataFunctionAuthorityService.page(query);
        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:authority:info')")
    public Result<DataFunctionAuthorityVO> get(@PathVariable("id") Long id){
        DataFunctionAuthorityEntity entity = dataFunctionAuthorityService.getById(id);

        return Result.ok(DataFunctionAuthorityConvert.INSTANCE.convert(entity));
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('sys:authority:save')")
    public Result<String> save(@RequestBody DataFunctionAuthorityVO vo){
        dataFunctionAuthorityService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('sys:authority:update')")
    public Result<String> update(@RequestBody @Valid DataFunctionAuthorityVO vo){
        dataFunctionAuthorityService.update(vo);
        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('sys:authority:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        dataFunctionAuthorityService.delete(idList);
        return Result.ok();
    }

    @PostMapping("make")
    @Operation(summary = "授权、取消授权")
    public Result<String> make(@RequestBody DataFunctionAuthorityVO data){
        dataFunctionAuthorityService.make(data);
        return Result.ok();
    }
}