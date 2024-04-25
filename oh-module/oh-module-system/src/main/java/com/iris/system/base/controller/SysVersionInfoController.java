package com.iris.system.base.controller;

import com.iris.system.base.query.SysVersionInfoQuery;
import com.iris.system.base.vo.SysVersionInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.common.utils.Result;
import com.iris.system.base.convert.SysVersionInfoConvert;
import com.iris.system.base.entity.SysVersionInfoEntity;
import com.iris.system.base.service.SysVersionInfoService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 版本信息
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-09-16
*/
@RestController
@RequestMapping("sys/info")
@Tag(name="版本信息")
public class SysVersionInfoController {

    @Resource
    private SysVersionInfoService sysVersionInfoService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('system:info:page')")
    public Result<PageResult<SysVersionInfoVO>> page(@ParameterObject @Valid SysVersionInfoQuery query){
        PageResult<SysVersionInfoVO> page = sysVersionInfoService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('system:info:info')")
    public Result<SysVersionInfoVO> get(@PathVariable("id") Long id){
        SysVersionInfoEntity entity = sysVersionInfoService.getById(id);

        return Result.ok(SysVersionInfoConvert.INSTANCE.convert(entity));
    }

    @GetMapping("latestVersion")
    @Operation(summary = "最新版本信息")
    public Result<SysVersionInfoVO> latestVersion(){
        SysVersionInfoEntity entity = sysVersionInfoService.latestVersion();
        return Result.ok(SysVersionInfoConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('system:info:save')")
    public Result<String> save(@RequestBody SysVersionInfoVO vo){
        sysVersionInfoService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('system:info:update')")
    public Result<String> update(@RequestBody @Valid SysVersionInfoVO vo){
        sysVersionInfoService.update(vo);
        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('system:info:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        sysVersionInfoService.delete(idList);
        return Result.ok();
    }
}