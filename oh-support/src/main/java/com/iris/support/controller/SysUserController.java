package com.iris.support.controller;

import com.iris.core.exception.ServerException;
import com.iris.framework.operatelog.annotations.OperateLog;
import com.iris.framework.operatelog.enums.OperateTypeEnum;
import com.iris.core.utils.PageResult;
import com.iris.core.utils.Result;
import com.iris.framework.security.user.SecurityUser;
import com.iris.framework.security.user.UserDetail;
import com.iris.support.query.SysUserQuery;
import com.iris.support.service.SysUserService;
import com.iris.support.vo.SysUserPasswordVO;
import com.iris.support.vo.SysUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 用户管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("sys/user")
@Tag(name = "用户管理")
public class SysUserController {
    private final SysUserService sysUserService;
    private final PasswordEncoder passwordEncoder;

    public SysUserController(SysUserService sysUserService, PasswordEncoder passwordEncoder) {
        this.sysUserService = sysUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result<PageResult<SysUserVO>> page(@ParameterObject @Valid SysUserQuery query) {
        PageResult<SysUserVO> page = sysUserService.page(query);

        return Result.ok(page);
    }

    @GetMapping("clockPage")
    @Operation(summary = "被锁定的用户列表")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result<PageResult<SysUserVO>> clockPage(@ParameterObject @Valid SysUserQuery query) {
        PageResult<SysUserVO> page = sysUserService.clockPage(query);
        return Result.ok(page);
    }

    @GetMapping("unlock/{userName}")
    @Operation(summary = "解锁用户")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result<String> unlock(@PathVariable("userName")String userName) {
        sysUserService.unlock(userName);
        return Result.ok();
    }
    // 绑定/解绑 租户的管理用户
    @PostMapping("/tenantUser/{tenantID}/{flag}")
    @Operation(summary = "管理租户用户,flag 1 绑定 2 解绑")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result<String> tenantUser(@PathVariable("tenantID") String tenantID, @PathVariable("flag")Integer flag, @RequestBody List<Long> userIdList) {
        sysUserService.updateTenantUser(tenantID, userIdList, flag);
        return Result.ok();
    }

    // 根据用户ID获取用户信息
    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:user:info')")
    public Result<SysUserVO> get(@PathVariable("id") Long id) {
        return Result.ok(sysUserService.info(id));
    }

    // 获取当前登录的用户
    @GetMapping("info")
    @Operation(summary = "登录用户")
    public Result<SysUserVO> info() {
        return Result.ok(sysUserService.info(null));
    }

    @PutMapping("password")
    @Operation(summary = "修改密码")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    public Result<String> password(@RequestBody @Valid SysUserPasswordVO vo) {
        // 原密码不正确
        UserDetail user = SecurityUser.getUser();
        if(user == null){
            throw new ServerException("请先登录！");
        }
        if (!passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            return Result.error("原密码不正确");
        }
        // 修改密码
        sysUserService.updatePassword(user.getId(), vo.getNewPassword());
        return Result.ok();
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:user:save')")
    public Result<String> save(@RequestBody @Valid SysUserVO vo) {
        // 新增密码不能为空
        if (ObjectUtils.isEmpty(vo.getPassword())) {
            return Result.error("密码不能为空");
        }
        // 保存
        sysUserService.save(vo);
        return Result.ok();
    }

    @PostMapping("register")
    @Operation(summary = "用户注册")
    @OperateLog(type = OperateTypeEnum.INSERT)
    public Result<String> register(@RequestBody @Valid SysUserVO vo) {
        // 新增密码不能为空
        if (ObjectUtils.isEmpty(vo.getPassword())) {
            return Result.error("密码不能为空");
        }
        // 自己注册的用户不分配角色，由管理员进行配置
        vo.setRoleIdList(null);
        // 保存
        sysUserService.save(vo);
        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result<String> update(@RequestBody @Valid SysUserVO vo) {
        sysUserService.update(vo);
        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        Long userId = SecurityUser.getUserId();
        if (idList.contains(userId)) {
            return Result.error("不能删除当前登录用户");
        }

        sysUserService.delete(idList);

        return Result.ok();
    }

    @PostMapping("import")
    @Operation(summary = "导入用户")
    @OperateLog(type = OperateTypeEnum.IMPORT)
    @PreAuthorize("hasAuthority('sys:user:import')")
    public Result<String> importExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("请选择需要上传的文件");
        }
        sysUserService.importByExcel(file, passwordEncoder.encode("123456"));

        return Result.ok();
    }

    @GetMapping("export")
    @Operation(summary = "导出用户")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    @PreAuthorize("hasAuthority('sys:user:export')")
    public void export() {
        sysUserService.export();
    }
}
