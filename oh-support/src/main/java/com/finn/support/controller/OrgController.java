package com.finn.support.controller;

import com.finn.framework.operatelog.annotations.OperateLog;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.constant.Constant;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.support.convert.OrgConvert;
import com.finn.support.entity.OrgEntity;
import com.finn.support.query.OrgQuery;
import com.finn.support.service.OrgService;
import com.finn.support.vo.OrgVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
public class OrgController {
    private final OrgService orgService;

    public OrgController(OrgService orgService) {
        this.orgService = orgService;
    }

    @GetMapping("list")
    @Operation(summary = "树形列表")
    @PreAuthorize("hasAuthority('sys:org:list')")
    public Result<List<OrgVO>> list(@ParameterObject OrgQuery query) {
        List<OrgVO> list = orgService.getList(query);
        return Result.ok(list);
    }

    @GetMapping("/page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:org:list')")
    public Result<PageResult<OrgVO>> page(@ParameterObject @Valid OrgQuery query) {
        PageResult<OrgVO> page = orgService.page(query);
        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:org:info')")
    public Result<OrgVO> get(@PathVariable("id") Long id) {
        OrgEntity entity = orgService.getById(id);
        OrgVO vo = OrgConvert.INSTANCE.convert(entity);

        // 获取上级机构名称
        if (!Constant.ROOT.equals(entity.getParentId())) {
            OrgEntity parentEntity = orgService.getById(entity.getParentId());
            vo.setParentName(parentEntity.getName());
        }

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(module = "机构管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:org:save')")
    public Result<String> save(@RequestBody @Valid OrgVO vo) {
        orgService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(module = "机构管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:org:update')")
    public Result<String> update(@RequestBody @Valid OrgVO vo) {
        orgService.update(vo);

        return Result.ok();
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除")
    @OperateLog(module = "机构管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:org:delete')")
    public Result<String> delete(@PathVariable("id") Long id) {
        orgService.delete(id);

        return Result.ok();
    }

}