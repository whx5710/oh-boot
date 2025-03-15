package com.finn.sys.base.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.sys.base.convert.VersionInfoConvert;
import com.finn.sys.base.entity.VersionInfoEntity;
import com.finn.sys.base.query.VersionInfoQuery;
import com.finn.sys.base.service.VersionInfoService;
import com.finn.sys.base.vo.VersionInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class VersionInfoController {

    @Resource
    private VersionInfoService versionInfoService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('system:info:page')")
    public Result<PageResult<VersionInfoVO>> page(@ParameterObject @Valid VersionInfoQuery query){
        PageResult<VersionInfoVO> page = versionInfoService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('system:info:info')")
    public Result<VersionInfoVO> get(@PathVariable("id") Long id){
        VersionInfoEntity entity = versionInfoService.getById(id);

        return Result.ok(VersionInfoConvert.INSTANCE.convert(entity));
    }

    @GetMapping("latestVersion")
    @Operation(summary = "最新版本信息")
    public Result<VersionInfoVO> latestVersion(){
        VersionInfoEntity entity = versionInfoService.latestVersion();
        return Result.ok(VersionInfoConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('system:info:save')")
    public Result<String> save(@RequestBody VersionInfoVO vo){
        versionInfoService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('system:info:update')")
    public Result<String> update(@RequestBody @Valid VersionInfoVO vo){
        versionInfoService.update(vo);
        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('system:info:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        versionInfoService.delete(idList);
        return Result.ok();
    }
}