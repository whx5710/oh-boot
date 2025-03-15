package com.finn.support.controller;

import com.finn.core.exception.ServerException;
import com.finn.core.utils.Tools;
import com.finn.framework.operatelog.annotations.OperateLog;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import com.finn.support.query.UserQuery;
import com.finn.support.service.UserService;
import com.finn.support.vo.UserPasswordVO;
import com.finn.support.vo.UserVO;
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
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result<PageResult<UserVO>> page(@ParameterObject @Valid UserQuery query) {
        PageResult<UserVO> page = userService.page(query);

        return Result.ok(page);
    }

    @GetMapping("clockPage")
    @Operation(summary = "被锁定的用户列表")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result<PageResult<UserVO>> clockPage(@ParameterObject @Valid UserQuery query) {
        PageResult<UserVO> page = userService.clockPage(query);
        return Result.ok(page);
    }

    @GetMapping("unlock/{userName}")
    @Operation(summary = "解锁用户")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result<String> unlock(@PathVariable("userName")String userName) {
        userService.unlock(userName);
        return Result.ok("操作成功");
    }
    // 绑定/解绑 租户的管理用户
    @PostMapping("/tenantUser/{tenantID}/{flag}")
    @Operation(summary = "管理租户用户,flag 1 绑定 2 解绑")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result<String> tenantUser(@PathVariable("tenantID") String tenantID, @PathVariable("flag")Integer flag, @RequestBody List<Long> userIdList) {
        userService.updateTenantUser(tenantID, userIdList, flag);
        return Result.ok("操作成功");
    }

    // 根据用户ID获取用户信息
    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:user:info')")
    public Result<UserVO> get(@PathVariable("id") Long id) {
        return Result.ok(userService.info(id));
    }

    // 获取当前登录的用户
    @GetMapping("info")
    @Operation(summary = "登录用户")
    public Result<UserVO> info() {
        return Result.ok(userService.info(null));
    }

    @PutMapping("password")
    @Operation(summary = "修改密码")
    @OperateLog(module = "用户管理", name = "修改密码", type = OperateTypeEnum.UPDATE)
    public Result<String> password(@RequestBody @Valid UserPasswordVO vo) {
        // 原密码不正确
        UserDetail user = SecurityUser.getUser();
        if(user == null){
            throw new ServerException("请先登录！");
        }
        if (!passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            return Result.error("原密码不正确");
        }
        // 修改密码
        userService.updatePassword(user.getId(), vo.getNewPassword());
        return Result.ok("修改成功");
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(module = "用户管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:user:save')")
    public Result<String> save(@RequestBody @Valid UserVO vo) {
        // 新增密码
        String msg = "新增成功！";
        if (ObjectUtils.isEmpty(vo.getPassword())) {
            // return Result.error("密码不能为空");
            String pwd = Tools.getRandom(8);
            vo.setPassword(pwd);
            msg = msg + "密码为" + pwd;
        }
        // 保存
        userService.save(vo);
        return Result.ok(msg);
    }

    @PostMapping("register")
    @Operation(summary = "用户注册")
    @OperateLog(module = "用户管理", name = "用户注册", type = OperateTypeEnum.INSERT)
    public Result<String> register(@RequestBody @Valid UserVO vo) {
        // 新增密码不能为空
        if (ObjectUtils.isEmpty(vo.getPassword())) {
            return Result.error("密码不能为空");
        }
        // 自己注册的用户不分配角色，由管理员进行配置
        vo.setRoleIdList(null);
        // 保存
        userService.save(vo);
        return Result.ok("提交成功");
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(module = "用户管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result<String> update(@RequestBody @Valid UserVO vo) {
        userService.update(vo);
        return Result.ok("修改成功");
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(module = "用户管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        Long userId = SecurityUser.getUserId();
        if (idList.contains(userId)) {
            return Result.error("不能删除当前登录用户");
        }

        userService.delete(idList);

        return Result.ok("删除成功");
    }

    @GetMapping("/resetPwd/{id}")
    @Operation(summary = "重置密码")
    @OperateLog(module = "用户管理", name = "重置密码", type = OperateTypeEnum.GET)
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result<String> resetPwd(@PathVariable("id") Long id){
        return Result.ok(userService.resetPwd(id));
    }

    @PostMapping("import")
    @Operation(summary = "导入用户")
    @OperateLog(module = "用户管理", name = "导入用户", type = OperateTypeEnum.IMPORT)
    @PreAuthorize("hasAuthority('sys:user:import')")
    public Result<String> importExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("请选择需要上传的文件");
        }
        userService.importByExcel(file, passwordEncoder.encode("123456"));

        return Result.ok("导入成功");
    }

    @GetMapping("export")
    @Operation(summary = "导出用户")
    @OperateLog(module = "用户管理", name = "导出用户", type = OperateTypeEnum.EXPORT)
    @PreAuthorize("hasAuthority('sys:user:export')")
    public void export() {
        userService.export();
    }
}
