package com.finn.system.controller;

import com.finn.core.exception.ServerException;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.core.utils.Tools;
import com.finn.framework.operatelog.annotations.Log;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import com.finn.system.query.UserQuery;
import com.finn.system.service.UserService;
import com.finn.system.vo.UserPasswordVO;
import com.finn.system.vo.UserVO;
import jakarta.validation.Valid;
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
 * @since 2024
 * 
 */
@RestController
@RequestMapping("sys/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 用户列表-分页
     * @param query 查询参数
     * @return 用户列表
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result<PageResult<UserVO>> page(@Valid UserQuery query) {
        PageResult<UserVO> page = userService.page(query);
        return Result.ok(page);
    }

    /**
     * 被锁定的用户列表
     * @param query 查询参数
     * @return 用户列表
     */
    @GetMapping("clockPage")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result<PageResult<UserVO>> clockPage(@Valid UserQuery query) {
        PageResult<UserVO> page = userService.clockPage(query);
        return Result.ok(page);
    }

    /**
     * 解锁用户
     * @param userName 用户名
     * @return 提示信息
     */
    @GetMapping("unlock/{userName}")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result<String> unlock(@PathVariable("userName")String userName) {
        userService.unlock(userName);
        return Result.ok("操作成功");
    }

    /**
     * 解绑租户的管理用户
     * @param tenantId 租户ID
     * @param userIdList 用户列表
     * @return 提示信息
     */
    @PostMapping("/unBindTenantUser/{tenantId}")
    @PreAuthorize("hasAuthority('sys:user:unBindTenantUser')")
    public Result<String> unBindTenantUser(@PathVariable("tenantId") String tenantId, @RequestBody List<Long> userIdList) {
        userService.unBindTenantUser(tenantId, userIdList);
        return Result.ok("操作成功");
    }

    /**
     * 绑定租户的管理用户
     * @param tenantId 租户ID
     * @param userIdList 用户列表
     * @return 提示信息
     */
    @PostMapping("/bindTenantUser/{tenantId}")
    @PreAuthorize("hasAuthority('sys:user:bindTenantUser')")
    public Result<String> bindTenantUser(@PathVariable("tenantId") String tenantId, @RequestBody List<Long> userIdList) {
        userService.bindTenantUser(tenantId, userIdList);
        return Result.ok("操作成功");
    }

    /**
     * 根据用户ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:user:info')")
    public Result<UserVO> get(@PathVariable("id") Long id) {
        return Result.ok(userService.info(id));
    }

    /**
     * 获取当前登录的用户
     * @return 用户信息
     */
    @GetMapping("info")
    public Result<UserVO> info() {
        return Result.ok(userService.info(null));
    }

    /**
     * 修改密码
     * @param vo 用户密码信息
     * @return 提示信息
     */
    @PostMapping("/password")
    @Log(module = "用户管理", name = "修改密码", type = OperateTypeEnum.UPDATE)
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

    /**
     * 保存用户
     * @param vo 用户信息
     * @return 提示信息
     */
    @PostMapping
    @Log(module = "用户管理", name = "保存", type = OperateTypeEnum.INSERT)
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

    /**
     * 用户注册
     * @param vo 用户信息
     * @return 提示信息
     */
    @PostMapping("register")
    @Log(module = "用户管理", name = "用户注册", type = OperateTypeEnum.INSERT)
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

    /**
     * 修改用户信息
     * @param vo 用户
     * @return 提示信息
     */
    @PostMapping("/update")
    @Log(module = "用户管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result<String> update(@RequestBody @Valid UserVO vo) {
        userService.update(vo);
        return Result.ok("修改成功");
    }

    /**
     * 删除用户
     * @param idList 用户ID
     * @return 提示信息
     */
    @PostMapping("/del")
    @Log(module = "用户管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        Long userId = SecurityUser.getUserId();
        if (idList.contains(userId)) {
            return Result.error("不能删除当前登录用户");
        }
        userService.delete(idList);
        return Result.ok("删除成功");
    }

    /**
     * 重置密码
     * @param id 用户ID
     * @return 提示信息
     */
    @GetMapping("/resetPwd/{id}")
    @Log(module = "用户管理", name = "重置密码", type = OperateTypeEnum.GET)
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result<String> resetPwd(@PathVariable("id") Long id){
        return Result.ok(userService.resetPwd(id));
    }

    /**
     * 导入用户
     * @param file excel文件
     * @return 提示信息
     */
    @PostMapping("import")
    @Log(module = "用户管理", name = "导入用户", type = OperateTypeEnum.IMPORT)
    @PreAuthorize("hasAuthority('sys:user:import')")
    public Result<String> importExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("请选择需要上传的文件");
        }
        userService.importByExcel(file, passwordEncoder.encode("123456"));

        return Result.ok("导入成功");
    }

    /**
     * 导出用户
     */
    @GetMapping("export")
    @Log(module = "用户管理", name = "导出用户", type = OperateTypeEnum.EXPORT)
    @PreAuthorize("hasAuthority('sys:user:export')")
    public void export(@Valid UserQuery query) {
        userService.export(query);
    }
}
