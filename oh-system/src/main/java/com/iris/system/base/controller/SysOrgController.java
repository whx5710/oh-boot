package com.iris.system.base.controller;

import com.iris.framework.common.utils.PageResult;
import com.iris.system.base.query.SysOrgQuery;
import com.iris.system.base.vo.SysOrgVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.common.utils.Result;
import com.iris.framework.operatelog.annotations.OperateLog;
import com.iris.framework.operatelog.enums.OperateTypeEnum;
import com.iris.system.base.convert.SysOrgConvert;
import com.iris.system.base.entity.SysOrgEntity;
import com.iris.system.base.service.SysOrgService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 机构管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("sys/org")
@Tag(name = "机构管理")
public class SysOrgController {
    private final SysOrgService sysOrgService;

    public SysOrgController(SysOrgService sysOrgService) {
        this.sysOrgService = sysOrgService;
    }

    @GetMapping("list")
    @Operation(summary = "树形列表")
    @PreAuthorize("hasAuthority('sys:org:list')")
    public Result<List<SysOrgVO>> list(@ParameterObject SysOrgQuery query) {
        List<SysOrgVO> list = sysOrgService.getList(query);
        return Result.ok(list);
    }

    @GetMapping("/page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:org:list')")
    public Result<PageResult<SysOrgVO>> page(@ParameterObject @Valid SysOrgQuery query) {
        PageResult<SysOrgVO> page = sysOrgService.page(query);
        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:org:info')")
    public Result<SysOrgVO> get(@PathVariable("id") Long id) {
        SysOrgEntity entity = sysOrgService.getById(id);
        SysOrgVO vo = SysOrgConvert.INSTANCE.convert(entity);

        // 获取上级机构名称
        if (!Constant.ROOT.equals(entity.getParentId())) {
            SysOrgEntity parentEntity = sysOrgService.getById(entity.getParentId());
            vo.setParentName(parentEntity.getName());
        }

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:org:save')")
    public Result<String> save(@RequestBody @Valid SysOrgVO vo) {
        sysOrgService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:org:update')")
    public Result<String> update(@RequestBody @Valid SysOrgVO vo) {
        sysOrgService.update(vo);

        return Result.ok();
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:org:delete')")
    public Result<String> delete(@PathVariable("id") Long id) {
        sysOrgService.delete(id);

        return Result.ok();
    }

}