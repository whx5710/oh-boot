package com.finn.support.controller;

import com.finn.framework.operatelog.annotations.OperateLog;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.support.convert.SysDictTypeConvert;
import com.finn.support.entity.SysDictTypeEntity;
import com.finn.support.query.SysDictTypeQuery;
import com.finn.support.service.SysDictTypeService;
import com.finn.support.vo.SysDictTypeVO;
import com.finn.support.vo.SysDictVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典类型
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("sys/dict/type")
@Tag(name = "字典类型")
public class SysDictTypeController {
    private final SysDictTypeService sysDictTypeService;

    public SysDictTypeController(SysDictTypeService sysDictTypeService) {
        this.sysDictTypeService = sysDictTypeService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<PageResult<SysDictTypeVO>> page(@ParameterObject @Valid SysDictTypeQuery query) {
        PageResult<SysDictTypeVO> page = sysDictTypeService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list/sql")
    @Operation(summary = "动态SQL数据")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<PageResult<SysDictVO.DictData>> listSql(Long id) {
        List<SysDictVO.DictData> list = sysDictTypeService.getDictSql(id);

        PageResult<SysDictVO.DictData> page = new PageResult<>(list, list.size());

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:dict:info')")
    public Result<SysDictTypeVO> get(@PathVariable("id") Long id) {
        SysDictTypeEntity entity = sysDictTypeService.getById(id);

        return Result.ok(SysDictTypeConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(module = "字典类型管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:dict:save')")
    public Result<String> save(@RequestBody @Valid SysDictTypeVO vo) {
        sysDictTypeService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(module = "字典类型管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:dict:update')")
    public Result<String> update(@RequestBody @Valid SysDictTypeVO vo) {
        sysDictTypeService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(module = "字典类型管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysDictTypeService.delete(idList);

        return Result.ok();
    }

    @GetMapping("all")
    @Operation(summary = "全部字典数据")
    public Result<List<SysDictVO>> all() {
        List<SysDictVO> dictList = sysDictTypeService.getDictList();

        return Result.ok(dictList);
    }

    @GetMapping("refreshTransCache")
    @Operation(summary = "刷新字典翻译缓存数据")
    @PreAuthorize("hasAuthority('sys:dict:refreshTransCache')")
    public Result<String> refreshTransCache() {
        sysDictTypeService.refreshTransCache();
        return Result.ok();
    }


}