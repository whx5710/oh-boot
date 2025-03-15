package com.finn.support.controller;

import com.finn.framework.operatelog.annotations.OperateLog;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.support.convert.DictTypeConvert;
import com.finn.support.entity.DictTypeEntity;
import com.finn.support.query.DictTypeQuery;
import com.finn.support.service.DictTypeService;
import com.finn.support.vo.DictTypeVO;
import com.finn.support.vo.DictVO;
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
public class DictTypeController {
    private final DictTypeService dictTypeService;

    public DictTypeController(DictTypeService dictTypeService) {
        this.dictTypeService = dictTypeService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<PageResult<DictTypeVO>> page(@ParameterObject @Valid DictTypeQuery query) {
        PageResult<DictTypeVO> page = dictTypeService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list/sql")
    @Operation(summary = "动态SQL数据")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<PageResult<DictVO.DictData>> listSql(Long id) {
        List<DictVO.DictData> list = dictTypeService.getDictSql(id);

        PageResult<DictVO.DictData> page = new PageResult<>(list, list.size());

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:dict:info')")
    public Result<DictTypeVO> get(@PathVariable("id") Long id) {
        DictTypeEntity entity = dictTypeService.getById(id);

        return Result.ok(DictTypeConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(module = "字典类型管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:dict:save')")
    public Result<String> save(@RequestBody @Valid DictTypeVO vo) {
        dictTypeService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(module = "字典类型管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:dict:update')")
    public Result<String> update(@RequestBody @Valid DictTypeVO vo) {
        dictTypeService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(module = "字典类型管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        dictTypeService.delete(idList);

        return Result.ok();
    }

    @GetMapping("all")
    @Operation(summary = "全部字典数据")
    public Result<List<DictVO>> all() {
        List<DictVO> dictList = dictTypeService.getDictList();

        return Result.ok(dictList);
    }

    @GetMapping("refreshTransCache")
    @Operation(summary = "刷新字典翻译缓存数据")
    @PreAuthorize("hasAuthority('sys:dict:refreshTransCache')")
    public Result<String> refreshTransCache() {
        dictTypeService.refreshTransCache();
        return Result.ok();
    }


}