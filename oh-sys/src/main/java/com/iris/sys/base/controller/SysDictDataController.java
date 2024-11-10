package com.iris.sys.base.controller;

import com.iris.sys.base.query.SysDictDataQuery;
import com.iris.sys.base.vo.SysDictDataVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.iris.core.utils.PageResult;
import com.iris.core.utils.Result;
import com.iris.framework.operatelog.annotations.OperateLog;
import com.iris.framework.operatelog.enums.OperateTypeEnum;
import com.iris.sys.base.convert.SysDictDataConvert;
import com.iris.sys.base.entity.SysDictDataEntity;
import com.iris.sys.base.service.SysDictDataService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典数据
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("sys/dict/data")
@Tag(name = "字典数据")
public class SysDictDataController {
    private final SysDictDataService sysDictDataService;

    public SysDictDataController(SysDictDataService sysDictDataService) {
        this.sysDictDataService = sysDictDataService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<PageResult<SysDictDataVO>> page(@ParameterObject @Valid SysDictDataQuery query) {
        PageResult<SysDictDataVO> page = sysDictDataService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:dict:info')")
    public Result<SysDictDataVO> get(@PathVariable("id") Long id) {
        SysDictDataEntity entity = sysDictDataService.getById(id);

        return Result.ok(SysDictDataConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:dict:save')")
    public Result<String> save(@RequestBody @Valid SysDictDataVO vo) {
        sysDictDataService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:dict:update')")
    public Result<String> update(@RequestBody @Valid SysDictDataVO vo) {
        sysDictDataService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysDictDataService.delete(idList);
        return Result.ok();
    }

}