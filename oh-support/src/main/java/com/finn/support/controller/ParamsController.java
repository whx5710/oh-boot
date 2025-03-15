package com.finn.support.controller;

import com.finn.framework.operatelog.annotations.OperateLog;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.support.convert.ParamsConvert;
import com.finn.support.entity.ParamsEntity;
import com.finn.support.query.ParamsQuery;
import com.finn.support.service.ParamsService;
import com.finn.support.vo.ParamsVO;
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
public class ParamsController {
    private final ParamsService paramsService;

    public ParamsController(ParamsService paramsService) {
        this.paramsService = paramsService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<PageResult<ParamsVO>> page(@ParameterObject @Valid ParamsQuery query) {
        PageResult<ParamsVO> page = paramsService.page(query);
        return Result.ok(page);
    }

    @GetMapping("/getByKey/{key}")
    public Result<String> getByKey(@PathVariable("key") String key){
        return Result.ok(paramsService.getString(key));
    }

    @PostMapping("/getByKeys")
    public Result<Map<String, String>> getByKeys(@RequestBody List<String> keys){
        return Result.ok(paramsService.getByKeys(keys));
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<ParamsVO> get(@PathVariable("id") Long id) {
        ParamsEntity entity = paramsService.getById(id);
        return Result.ok(ParamsConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(module = "参数管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<String> save(@RequestBody ParamsVO vo) {
        paramsService.save(vo);
        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(module = "参数管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<String> update(@RequestBody @Valid ParamsVO vo) {
        paramsService.update(vo);
        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(module = "参数管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        paramsService.delete(idList);
        return Result.ok();
    }
}