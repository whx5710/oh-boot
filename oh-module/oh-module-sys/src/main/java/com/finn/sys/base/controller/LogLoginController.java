package com.finn.sys.base.controller;

import com.finn.framework.operatelog.annotations.OperateLog;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.support.query.LogLoginQuery;
import com.finn.support.service.LogLoginService;
import com.finn.support.vo.LogLoginVO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("sys/log/login")
public class LogLoginController {

    @Resource
    private LogLoginService logLoginService;

    /**
     * 分页
     * @param query 查询参数
     * @return 集合
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:log:login')")
    public Result<PageResult<LogLoginVO>> page(@Valid LogLoginQuery query) {
        PageResult<LogLoginVO> page = logLoginService.page(query);
        return Result.ok(page);
    }

    /**
     * 导出excel
     */
    @GetMapping("export")
    @OperateLog(module = "登录日志", name = "导出excel", type = OperateTypeEnum.EXPORT)
    @PreAuthorize("hasAuthority('sys:log:login')")
    public void export() {
        logLoginService.export();
    }

}