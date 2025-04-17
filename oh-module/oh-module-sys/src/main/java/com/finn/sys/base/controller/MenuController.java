package com.finn.sys.base.controller;

import com.finn.framework.operatelog.annotations.OperateLog;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.constant.Constant;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import com.finn.support.service.UserRoleService;
import com.finn.sys.base.convert.MenuConvert;
import com.finn.sys.base.entity.MenuEntity;
import com.finn.sys.base.enums.MenuTypeEnum;
import com.finn.sys.base.query.MenuQuery;
import com.finn.sys.base.service.MenuService;
import com.finn.sys.base.vo.MenuTreeVO;
import com.finn.sys.base.vo.MenuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("sys/menu")
@Tag(name = "菜单管理")
public class MenuController {
    private final MenuService menuService;

    private final UserRoleService userRoleService;

    public MenuController(MenuService menuService, UserRoleService userRoleService) {
        this.menuService = menuService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("nav")
    @Operation(summary = "菜单导航")
    public Result<List<MenuTreeVO>> nav() {
        UserDetail user = SecurityUser.getUser();
        List<MenuTreeVO> list = menuService.getUserMenuList(user, MenuTypeEnum.MENU.getValue());

        return Result.ok(list);
    }

    @GetMapping("authority")
    @Operation(summary = "用户权限标识")
    public Result<Set<String>> authority() {
        UserDetail user = SecurityUser.getUser();
        Set<String> set = userRoleService.getUserAuthority(user);

        return Result.ok(set);
    }


    @GetMapping("listTree")
    @Operation(summary = "菜单列表-树形")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<List<MenuTreeVO>> listTree(@ParameterObject MenuQuery query) {
        List<MenuTreeVO> list = menuService.getMenuTreeList(query);
        return Result.ok(list);
    }

    @GetMapping("page")
    @Operation(summary = "菜单列表")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<PageResult<MenuVO>> page(@ParameterObject MenuQuery query) {
        return Result.ok(menuService.page(query));
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:menu:info')")
    public Result<MenuTreeVO> get(@PathVariable("id") Long id) {
        MenuEntity entity = menuService.getById(id);
        MenuTreeVO vo = MenuConvert.INSTANCE.convert(entity);

        // 获取上级菜单名称
        if (!Constant.ROOT.equals(entity.getParentId())) {
            MenuEntity parentEntity = menuService.getById(entity.getParentId());
            vo.setParentName(parentEntity.getName());
        }

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(module = "菜单管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result<String> save(@RequestBody @Valid MenuTreeVO vo) {
        menuService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(module = "菜单管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result<String> update(@RequestBody @Valid MenuTreeVO vo) {
        menuService.update(vo);

        return Result.ok();
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除")
    @OperateLog(module = "菜单管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result<String> delete(@PathVariable("id") Long id) {
        // 判断是否有子菜单或按钮
        Long count = menuService.getSubMenuCount(id);
        if (count > 0) {
            return Result.error("请先删除子菜单");
        }
        menuService.delete(id);
        return Result.ok();
    }


    @GetMapping("/role")
    @Operation(summary = "角色菜单")
    @PreAuthorize("hasAuthority('sys:role:menu')")
    public Result<List<MenuTreeVO>> roleMenu() {
        UserDetail user = SecurityUser.getUser();
        List<MenuTreeVO> list = menuService.getUserMenuList(user, null);
        return Result.ok(list);
    }
}