package com.iris.system.pim.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.common.utils.Result;
import com.iris.framework.operatelog.annotations.OperateLog;
import com.iris.framework.operatelog.enums.OperateTypeEnum;
import com.iris.framework.security.user.SecurityUser;
import com.iris.framework.security.user.UserDetail;
import com.iris.system.pim.convert.SysMenuConvert;
import com.iris.system.pim.entity.SysMenuEntity;
import com.iris.system.pim.enums.MenuTypeEnum;
import com.iris.system.pim.service.SysMenuService;
import com.iris.system.pim.vo.SysMenuVO;
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
    public Result<List<SysMenuVO>> nav() {
        UserDetail user = SecurityUser.getUser();
        List<SysMenuVO> list = sysMenuService.getUserMenuList(user, MenuTypeEnum.MENU.getValue());

        return Result.ok(list);
    }

    @GetMapping("authority")
    @Operation(summary = "用户权限标识")
    public Result<Set<String>> authority() {
        UserDetail user = SecurityUser.getUser();
        Set<String> set = sysMenuService.getUserAuthority(user);

        return Result.ok(set);
    }

    @GetMapping("list")
    @Operation(summary = "菜单列表")
    @Parameter(name = "type", description = "菜单类型 0：菜单 1：按钮  2：接口  null：全部")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<List<SysMenuVO>> list(Integer type) {
        List<SysMenuVO> list = sysMenuService.getMenuList(type);

        return Result.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:menu:info')")
    public Result<SysMenuVO> get(@PathVariable("id") Long id) {
        SysMenuEntity entity = sysMenuService.getById(id);
        SysMenuVO vo = SysMenuConvert.INSTANCE.convert(entity);

        // 获取上级菜单名称
        if (!Constant.ROOT.equals(entity.getPid())) {
            SysMenuEntity parentEntity = sysMenuService.getById(entity.getPid());
            vo.setParentName(parentEntity.getName());
        }

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result<String> save(@RequestBody @Valid SysMenuVO vo) {
        sysMenuService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result<String> update(@RequestBody @Valid SysMenuVO vo) {
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