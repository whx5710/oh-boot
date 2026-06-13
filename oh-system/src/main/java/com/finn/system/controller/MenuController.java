package com.finn.system.controller;

import com.finn.framework.aop.annotations.Log;
import com.finn.framework.common.enums.OperateTypeEnum;
import com.finn.framework.common.constant.Constant;
import com.finn.framework.entity.PageResult;
import com.finn.framework.entity.Result;
import com.finn.system.convert.MenuConvert;
import com.finn.system.entity.MenuEntity;
import com.finn.system.query.MenuQuery;
import com.finn.system.service.MenuService;
import com.finn.system.vo.MenuTreeVO;
import com.finn.system.vo.MenuVO;
import com.finn.system.vo.RouteVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 获取菜单
     * @param query 类型 catalog | menu | action | all
     * @return
     */
    @PostMapping("/route")
    public Result<List<RouteVO>> route(@RequestBody MenuQuery query) {
        return Result.ok(menuService.getUserRouteList(query));
    }

    /**
     * 菜单列表-树形
     * @param query 查询条件
     * @return 集合
     */
    @GetMapping("/listTree")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<List<MenuTreeVO>> listTree(MenuQuery query) {
        List<MenuTreeVO> list = menuService.getMenuTreeList(query);
        return Result.ok(list);
    }

    /**
     * 菜单列表
     * @param query 查询条件
     * @return 集合
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<PageResult<MenuVO>> page(MenuQuery query) {
        return Result.ok(menuService.page(query));
    }

    /**
     * 根据ID获取菜单
     * @param id 菜单ID
     * @return 菜单数据
     */
    @GetMapping("/{id}")
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

    /**
     * 保存
     * @param vo 菜单信息
     * @return 提示信息
     */
    @PostMapping
    @Log(module = "菜单管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result<String> save(@RequestBody @Valid RouteVO vo) {
        menuService.save(vo);

        return Result.ok();
    }

    /**
     * 修改
     * @param vo 菜单信息
     * @return 提示信息
     */
    @PostMapping("/update")
    @Log(module = "菜单管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result<String> update(@RequestBody @Valid RouteVO vo) {
        menuService.update(vo);

        return Result.ok();
    }

    /**
     * 删除
     * @param id 菜单ID
     * @return
     */
    @PostMapping("/delById/{id}")
    @Log(module = "菜单管理", name = "删除", type = OperateTypeEnum.DELETE)
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

    /**
     * 角色菜单
     * @return 菜单列表
     */
    @GetMapping("/role")
    @PreAuthorize("hasAuthority('sys:role:menu')")
    public Result<List<MenuTreeVO>> roleMenu() {
        List<MenuTreeVO> list = menuService.getUserMenuList( null);
        return Result.ok(list);
    }

    /**
     * 菜单列表
     * @param query 查询菜单
     * @return 菜单列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<List<MenuVO>> list(MenuQuery query) {
        return Result.ok(menuService.list(query));
    }

    /**
     * 菜单名称是否存在
     * @param name 菜单名称
     * @param id 菜单ID（排除的）
     * @return 是否存在
     */
    @GetMapping("/nameExists")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<Boolean> nameExists(@RequestParam String name, @RequestParam(required = false) Long id) {
        return Result.ok(menuService.nameExists(id, name));
    }

    /**
     * 菜单路径是否存在
     * @param path 菜单路径
     * @param id 菜单ID（排除的）
     * @return 是否存在
     */
    @GetMapping("/pathExists")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<Boolean> pathExists(@RequestParam String path, @RequestParam(required = false) Long id) {
        return Result.ok(menuService.pathExists(id, path));
    }

}