package com.finn.team.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.team.convert.OhProjectConvert;
import com.finn.team.entity.OhProjectEntity;
import com.finn.team.query.OhProjectQuery;
import com.finn.team.service.OhProjectService;
import com.finn.team.vo.OhProjectVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 项目信息表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@RestController
@RequestMapping("team/project")
@Tag(name="项目信息表")
public class OhProjectController {
    private final OhProjectService ohProjectService;

    public OhProjectController(OhProjectService ohProjectService) {
        this.ohProjectService = ohProjectService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('team:project:page')")
    public Result<PageResult<OhProjectVO>> page(@Valid OhProjectQuery query){
        PageResult<OhProjectVO> page = ohProjectService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('team:project:info')")
    public Result<OhProjectVO> get(@PathVariable("id") Long id){
        OhProjectEntity entity = ohProjectService.getById(id);

        return Result.ok(OhProjectConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('team:project:save')")
    public Result<String> save(@RequestBody OhProjectVO vo){
        vo.setDbStatus(1);
        ohProjectService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('team:project:update')")
    public Result<String> update(@RequestBody @Valid OhProjectVO vo){
        ohProjectService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('team:project:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        ohProjectService.delete(idList);

        return Result.ok();
    }
}