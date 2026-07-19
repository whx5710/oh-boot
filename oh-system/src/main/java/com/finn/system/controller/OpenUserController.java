package com.finn.system.controller;

import com.finn.common.entity.Result;
import com.finn.system.service.OpenUserService;
import com.finn.system.vo.UserVO;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 *
 * @author 王小费 whx5710@qq.com
 * @since 2024
 * 
 */
@RestController
@RequestMapping("/sys/openUser")
public class OpenUserController {
    private final OpenUserService openUserService;

    public OpenUserController(OpenUserService openUserService) {
        this.openUserService = openUserService;
    }

    /**
     * 用户列表-分页
     * @param query 查询参数
     * @return 用户列表
     */
//    @GetMapping("/page")
//    @PreAuthorize("hasAuthority('sys:user:page')")
//    public Result<PageResult<UserVO>> page(@Valid UserQuery query) {
//        PageResult<UserVO> page = openUserService.page(query);
//        return Result.ok(page);
//    }

    /**
     * 根据用户ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
//    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('sys:user:info')")
//    public Result<UserVO> get(@PathVariable("id") Long id) {
//        return Result.ok(openUserService.info(id));
//    }

    /**
     * 获取当前登录的用户
     * @return 用户信息
     */
    @GetMapping("/info")
    public Result<UserVO> info() {
        return Result.ok(openUserService.info(null));
    }

    /**
     * 修改用户信息
     * @param vo 用户
     * @return 提示信息
     */
//    @PostMapping("/update")
//    @Log(module = "用户管理", name = "修改", type = OperateTypeEnum.UPDATE)
//    @PreAuthorize("hasAuthority('sys:user:update')")
//    public Result<String> update(@RequestBody @Valid UserVO vo) {
//        openUserService.update(vo);
//        return Result.ok("修改成功");
//    }

    /**
     * 删除用户
     * @param idList 用户ID
     * @return 提示信息
     */
//    @PostMapping("/del")
//    @Log(module = "用户管理", name = "删除", type = OperateTypeEnum.DELETE)
//    @PreAuthorize("hasAuthority('sys:user:delete')")
//    public Result<String> delete(@RequestBody List<Long> idList) {
//        Long userId = SecurityUser.getUserId();
//        if (idList.contains(userId)) {
//            return Result.error("不能删除当前登录用户");
//        }
//        openUserService.delete(idList);
//        return Result.ok("删除成功");
//    }
}
