package com.iris.sys.base.controller;

import com.iris.common.operatelog.annotations.OperateLog;
import com.iris.common.operatelog.enums.OperateTypeEnum;
import com.iris.core.utils.PageResult;
import com.iris.core.utils.Result;
import com.iris.support.convert.SysParamsConvert;
import com.iris.support.entity.SysParamsEntity;
import com.iris.support.query.SysParamsQuery;
import com.iris.support.service.SysParamsService;
import com.iris.support.vo.SysParamsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 参数管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("sys/params")
@Tag(name = "参数管理")
public class SysParamsController {
    private final SysParamsService sysParamsService;

    public SysParamsController(SysParamsService sysParamsService) {
        this.sysParamsService = sysParamsService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<PageResult<SysParamsVO>> page(@ParameterObject @Valid SysParamsQuery query) {
        PageResult<SysParamsVO> page = sysParamsService.page(query);
        return Result.ok(page);
    }

    @GetMapping("/getByKey/{key}")
    public Result<String> getByKey(@PathVariable("key") String key){
        return Result.ok(sysParamsService.getString(key));
    }

    @PostMapping("/getByKeys")
    public Result<Map<String, String>> getByKeys(@RequestBody List<String> keys){
        return Result.ok(sysParamsService.getByKeys(keys));
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<SysParamsVO> get(@PathVariable("id") Long id) {
        SysParamsEntity entity = sysParamsService.getById(id);
        return Result.ok(SysParamsConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<String> save(@RequestBody SysParamsVO vo) {
        sysParamsService.save(vo);
        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<String> update(@RequestBody @Valid SysParamsVO vo) {
        sysParamsService.update(vo);
        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysParamsService.delete(idList);
        return Result.ok();
    }
}