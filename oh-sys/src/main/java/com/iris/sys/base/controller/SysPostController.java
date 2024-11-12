package com.iris.sys.base.controller;

import com.iris.sys.base.query.SysPostQuery;
import com.iris.sys.base.vo.SysPostVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.iris.core.utils.PageResult;
import com.iris.core.utils.Result;
import com.iris.common.operatelog.annotations.OperateLog;
import com.iris.common.operatelog.enums.OperateTypeEnum;
import com.iris.sys.base.convert.SysPostConvert;
import com.iris.sys.base.entity.SysPostEntity;
import com.iris.sys.base.service.SysPostService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 岗位管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("sys/post")
@Tag(name = "岗位管理")
public class SysPostController {
    private final SysPostService sysPostService;

    public SysPostController(SysPostService sysPostService) {
        this.sysPostService = sysPostService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:post:page')")
    public Result<PageResult<SysPostVO>> page(@ParameterObject @Valid SysPostQuery query) {
        PageResult<SysPostVO> page = sysPostService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    public Result<List<SysPostVO>> list() {
        List<SysPostVO> list = sysPostService.getList();

        return Result.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:post:info')")
    public Result<SysPostVO> get(@PathVariable("id") Long id) {
        SysPostEntity entity = sysPostService.getById(id);

        return Result.ok(SysPostConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:post:save')")
    public Result<String> save(@RequestBody SysPostVO vo) {
        sysPostService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:post:update')")
    public Result<String> update(@RequestBody @Valid SysPostVO vo) {
        sysPostService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:post:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysPostService.delete(idList);

        return Result.ok();
    }
}