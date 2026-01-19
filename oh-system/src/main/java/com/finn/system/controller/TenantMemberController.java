package com.finn.system.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.system.convert.TenantMemberConvert;
import com.finn.system.entity.TenantMemberEntity;
import com.finn.system.query.TenantMemberQuery;
import com.finn.system.service.TenantMemberService;
import com.finn.system.vo.TenantMemberVO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
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
public class TenantMemberController {

    @Resource
    private TenantMemberService tenantMemberService;

    /**
     * 分页
     * @param query 查询参数
     * @return 集合
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('tenant:member:page')")
    public Result<PageResult<TenantMemberVO>> page(@Valid TenantMemberQuery query){
        PageResult<TenantMemberVO> page = tenantMemberService.page(query);
        return Result.ok(page);
    }

    /**
     * 根据ID获取租户信息
     * @param id 租户ID
     * @return 租户信息
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('tenant:member:info')")
    public Result<TenantMemberVO> get(@PathVariable("id") Long id){
        TenantMemberEntity entity = tenantMemberService.getById(id);

        return Result.ok(TenantMemberConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存租户
     * @param vo 租户信息
     * @return 提示信息
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('tenant:member:save')")
    public Result<String> save(@RequestBody TenantMemberVO vo){
        tenantMemberService.save(vo);
        return Result.ok();
    }

    /**
     * 修改租户信息
     * @param vo 租户信息
     * @return 提示信息
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('tenant:member:update')")
    public Result<String> update(@RequestBody @Valid TenantMemberVO vo){
        tenantMemberService.update(vo);
        return Result.ok();
    }

    /**
     * 根据ID删除租户信息
     * @param id 租户ID
     * @return 提示信息
     */
    @PostMapping("/delById/{id}")
    @PreAuthorize("hasAuthority('tenant:member:update')")
    public Result<String> delete(@PathVariable("id") Long id){
        tenantMemberService.delete(id);
        return Result.ok();
    }
}