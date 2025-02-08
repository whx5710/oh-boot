package com.finn.sys.base.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.sys.base.convert.TenantMemberConvert;
import com.finn.sys.base.entity.TenantMemberEntity;
import com.finn.sys.base.query.TenantMemberQuery;
import com.finn.sys.base.service.TenantMemberService;
import com.finn.sys.base.vo.TenantMemberVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 租户信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 2025-01-18
 */
@RestController
@RequestMapping("/tenant/member")
@Tag(name="版本信息")
public class TenantMemberController {

    @Resource
    private TenantMemberService tenantMemberService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('tenant:member:page')")
    public Result<PageResult<TenantMemberVO>> page(@ParameterObject @Valid TenantMemberQuery query){
        PageResult<TenantMemberVO> page = tenantMemberService.page(query);
        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('tenant:member:info')")
    public Result<TenantMemberVO> get(@PathVariable("id") Long id){
        TenantMemberEntity entity = tenantMemberService.getById(id);

        return Result.ok(TenantMemberConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('tenant:member:save')")
    public Result<String> save(@RequestBody TenantMemberVO vo){
        tenantMemberService.save(vo);
        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('tenant:member:update')")
    public Result<String> update(@RequestBody @Valid TenantMemberVO vo){
        tenantMemberService.update(vo);
        return Result.ok();
    }
}