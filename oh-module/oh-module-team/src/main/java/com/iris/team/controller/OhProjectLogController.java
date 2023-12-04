package com.iris.team.controller;

import com.iris.team.convert.OhProjectLogConvert;
import com.iris.team.entity.OhProjectLogEntity;
import com.iris.team.query.OhProjectLogQuery;
import com.iris.team.service.OhProjectLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.iris.team.vo.OhProjectLogVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.common.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 项目、任务操作日志
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@RestController
@RequestMapping("team/log")
@Tag(name="项目、任务操作日志")
public class OhProjectLogController {
    private final OhProjectLogService ohProjectLogService;

    public OhProjectLogController(OhProjectLogService ohProjectLogService) {
        this.ohProjectLogService = ohProjectLogService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('team:log:page')")
    public Result<PageResult<OhProjectLogVO>> page(@Valid OhProjectLogQuery query){
        PageResult<OhProjectLogVO> page = ohProjectLogService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('team:log:info')")
    public Result<OhProjectLogVO> get(@PathVariable("id") Long id){
        OhProjectLogEntity entity = ohProjectLogService.getById(id);

        return Result.ok(OhProjectLogConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('team:log:save')")
    public Result<String> save(@RequestBody OhProjectLogVO vo){
        ohProjectLogService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('team:log:update')")
    public Result<String> update(@RequestBody @Valid OhProjectLogVO vo){
        ohProjectLogService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('team:log:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        ohProjectLogService.delete(idList);

        return Result.ok();
    }
}