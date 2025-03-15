package com.finn.support.controller;

import com.finn.framework.operatelog.annotations.OperateLog;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.support.convert.RoleConvert;
import com.finn.support.entity.RoleEntity;
import com.finn.support.query.RoleQuery;
import com.finn.support.query.RoleUserQuery;
import com.finn.support.service.*;
import com.finn.support.vo.RoleDataScopeVO;
import com.finn.support.vo.RoleVO;
import com.finn.support.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("sys/role")
@Tag(name = "角色管理")
public class RoleController {
    private final RoleService roleService;
    private final UserService userService;
    private final RoleMenuService roleMenuService;
    private final RoleDataScopeService roleDataScopeService;
    private final UserRoleService userRoleService;

    public RoleController(RoleService roleService, UserService userService, RoleMenuService roleMenuService,
                          RoleDataScopeService roleDataScopeService, UserRoleService userRoleService) {
        this.roleService = roleService;
        this.userService = userService;
        this.roleMenuService = roleMenuService;
        this.roleDataScopeService = roleDataScopeService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:role:page')")
    public Result<PageResult<RoleVO>> page(@ParameterObject @Valid RoleQuery query) {
        PageResult<RoleVO> page = roleService.page(query);
        return Result.ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public Result<List<RoleVO>> list() {
        List<RoleVO> list = roleService.getList(new RoleQuery());

        return Result.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:role:info')")
    public Result<RoleVO> get(@PathVariable("id") Long id) {
        RoleEntity entity = roleService.getById(id);

        // 转换对象
        RoleVO role = RoleConvert.INSTANCE.convert(entity);

        // 查询角色对应的菜单
        List<Long> menuIdList = roleMenuService.getMenuIdList(id);
        role.setMenuIdList(menuIdList);

        // 查询角色对应的数据权限
        List<Long> orgIdList = roleDataScopeService.getOrgIdList(id);
        role.setOrgIdList(orgIdList);

        return Result.ok(role);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(module = "角色管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:role:save')")
    public Result<String> save(@RequestBody @Valid RoleVO vo) {
        roleService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(module = "角色管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<String> update(@RequestBody @Valid RoleVO vo) {
        roleService.update(vo);

        return Result.ok();
    }

    @PutMapping("data-scope")
    @Operation(summary = "数据权限")
    @OperateLog(module = "角色管理", name = "数据权限", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<String> dataScope(@RequestBody @Valid RoleDataScopeVO vo) {
        roleService.dataScope(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(module = "角色管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        roleService.delete(idList);

        return Result.ok();
    }

    @GetMapping("user/page")
    @Operation(summary = "角色用户-分页")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<PageResult<UserVO>> userPage(@Valid RoleUserQuery query) {
        PageResult<UserVO> page = userService.roleUserPage(query);

        return Result.ok(page);
    }

    @DeleteMapping("user/{roleId}")
    @Operation(summary = "删除角色用户")
    @OperateLog(module = "角色管理", name = "删除角色用户", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<String> userDelete(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIdList) {
        userRoleService.deleteByUserIdList(roleId, userIdList);

        return Result.ok();
    }

    @PostMapping("user/{roleId}")
    @Operation(summary = "分配角色给用户列表")
    @OperateLog(module = "角色管理", name = "分配角色给用户列表", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<String> userSave(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIdList) {
        userRoleService.saveUserList(roleId, userIdList);

        return Result.ok();
    }
}