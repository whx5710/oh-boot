package com.finn.system.controller;

import com.finn.framework.aop.annotations.Log;
import com.finn.framework.common.enums.OperateTypeEnum;
import com.finn.framework.entity.PageResult;
import com.finn.framework.entity.Result;
import com.finn.system.query.LoginLogQuery;
import com.finn.system.service.LoginLogService;
import com.finn.system.vo.LoginLogVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 登录日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("sys/log/login")
public class LoginLogController {

    private final LoginLogService loginLogService;

    public LoginLogController(LoginLogService loginLogService){
        this.loginLogService = loginLogService;
    }

    /**
     * 分页
     * @param query 查询参数
     * @return 集合
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:log:login')")
    public Result<PageResult<LoginLogVO>> page(@Valid LoginLogQuery query) {
        PageResult<LoginLogVO> page = loginLogService.page(query);
        return Result.ok(page);
    }

    /**
     * 导出excel
     */
    @GetMapping("export")
    @Log(module = "登录日志", name = "导出登录日志", type = OperateTypeEnum.EXPORT)
    @PreAuthorize("hasAuthority('sys:log:login')")
    public void export(LoginLogQuery query) {
        loginLogService.export(query);
    }

    /**
     * 删除
     * @param idList id集合
     * @return str
     */
    @PostMapping("/del")
    @PreAuthorize("hasAuthority('sys:log:login:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        loginLogService.delete(idList);
        return Result.ok();
    }

    /**
     * 根据日期删除日志（删除日期之前的数据）
     * @param date
     * @return
     */
    @GetMapping("/deleteByDate/{date}")
    @PreAuthorize("hasAuthority('sys:log:login:delete')")
    public Result<String> deleteByDate(@PathVariable("date")String date){
        loginLogService.deleteByDate(date);
        return Result.ok();
    }

}