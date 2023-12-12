package com.iris.team.controller;

import com.iris.framework.common.utils.PageResult;
import com.iris.team.convert.OhTaskUserConvert;
import com.iris.team.entity.OhTaskUserEntity;
import com.iris.team.query.OhTaskUserQuery;
import com.iris.team.service.OhTaskUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.iris.team.vo.OhTaskUserVO;
import com.iris.framework.common.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 任务人员表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@RestController
@RequestMapping("team/user")
@Tag(name="任务人员表")
public class OhTaskUserController {
    private final OhTaskUserService ohTaskUserService;

    public OhTaskUserController(OhTaskUserService ohTaskUserService) {
        this.ohTaskUserService = ohTaskUserService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('team:user:page')")
    public Result<PageResult<OhTaskUserVO>> page(@Valid OhTaskUserQuery query){
        PageResult<OhTaskUserVO> page = ohTaskUserService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('team:user:info')")
    public Result<OhTaskUserVO> get(@PathVariable("id") Long id){
        OhTaskUserEntity entity = ohTaskUserService.getById(id);

        return Result.ok(OhTaskUserConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('team:user:save')")
    public Result<String> save(@RequestBody OhTaskUserVO vo){
        ohTaskUserService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('team:user:update')")
    public Result<String> update(@RequestBody @Valid OhTaskUserVO vo){
        ohTaskUserService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('team:user:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        ohTaskUserService.delete(idList);

        return Result.ok();
    }
}