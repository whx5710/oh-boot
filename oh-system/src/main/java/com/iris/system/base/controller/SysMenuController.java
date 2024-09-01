package com.iris.system.base.controller;

import com.iris.framework.common.utils.PageResult;
import com.iris.system.base.enums.MenuTypeEnum;
import com.iris.system.base.query.SysMenuQuery;
import com.iris.system.base.vo.SysMenuTreeVO;
import com.iris.system.base.vo.SysMenuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.common.utils.Result;
import com.iris.framework.operatelog.annotations.OperateLog;
import com.iris.framework.operatelog.enums.OperateTypeEnum;
import com.iris.framework.security.user.SecurityUser;
import com.iris.framework.security.user.UserDetail;
import com.iris.system.base.convert.SysMenuConvert;
import com.iris.system.base.entity.SysMenuEntity;
import com.iris.system.base.service.SysMenuService;
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
public class SysMenuController {
    private final SysMenuService sysMenuService;

    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @GetMapping("nav")
    @Operation(summary = "菜单导航")
    public Result<List<SysMenuTreeVO>> nav() {
        UserDetail user = SecurityUser.getUser();
        List<SysMenuTreeVO> list = sysMenuService.getUserMenuList(user, MenuTypeEnum.MENU.getValue());

        return Result.ok(list);
    }

    @GetMapping("authority")
    @Operation(summary = "用户权限标识")
    public Result<Set<String>> authority() {
        UserDetail user = SecurityUser.getUser();
        Set<String> set = sysMenuService.getUserAuthority(user);

        return Result.ok(set);
    }


    @GetMapping("listTree")
    @Operation(summary = "菜单列表-树形")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<List<SysMenuTreeVO>> listTree(@ParameterObject SysMenuQuery query) {
        List<SysMenuTreeVO> list = sysMenuService.getMenuTreeList(query);
        return Result.ok(list);
    }

    @GetMapping("page")
    @Operation(summary = "菜单列表")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<PageResult<SysMenuVO>> page(@ParameterObject SysMenuQuery query) {
        return Result.ok(sysMenuService.page(query));
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:menu:info')")
    public Result<SysMenuTreeVO> get(@PathVariable("id") Long id) {
        SysMenuEntity entity = sysMenuService.getById(id);
        SysMenuTreeVO vo = SysMenuConvert.INSTANCE.convert(entity);

        // 获取上级菜单名称
        if (!Constant.ROOT.equals(entity.getParentId())) {
            SysMenuEntity parentEntity = sysMenuService.getById(entity.getParentId());
            vo.setParentName(parentEntity.getName());
        }

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result<String> save(@RequestBody @Valid SysMenuTreeVO vo) {
        sysMenuService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result<String> update(@RequestBody @Valid SysMenuTreeVO vo) {
        sysMenuService.update(vo);

        return Result.ok();
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result<String> delete(@PathVariable("id") Long id) {
        // 判断是否有子菜单或按钮
        Long count = sysMenuService.getSubMenuCount(id);
        if (count > 0) {
            return Result.error("请先删除子菜单");
        }

        sysMenuService.delete(id);

        return Result.ok();
    }
}