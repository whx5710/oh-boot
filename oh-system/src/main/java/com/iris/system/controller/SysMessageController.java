package com.iris.system.controller;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.common.utils.Result;
import com.iris.system.convert.SysMessageConvert;
import com.iris.system.entity.SysMessageEntity;
import com.iris.system.query.SysMessageQuery;
import com.iris.system.service.SysMessageService;
import com.iris.system.vo.SysMessageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 系统消息
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-10-10
*/
@RestController
@RequestMapping("message")
@Tag(name="系统消息")
public class SysMessageController {

    @Resource
    private SysMessageService sysMessageService;

    @GetMapping("page")
    @Operation(summary = "分页")
    public Result<PageResult<SysMessageVO>> page(@ParameterObject @Valid SysMessageQuery query){
        PageResult<SysMessageVO> page = sysMessageService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    public Result<SysMessageVO> get(@PathVariable("id") Long id){
        SysMessageEntity entity = sysMessageService.getById(id);

        return Result.ok(SysMessageConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    public Result<String> save(@RequestBody SysMessageVO vo){
        sysMessageService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    public Result<String> update(@RequestBody @Valid SysMessageVO vo){
        sysMessageService.update(vo);
        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    public Result<String> delete(@RequestBody List<Long> idList){
        sysMessageService.delete(idList);

        return Result.ok();
    }
}