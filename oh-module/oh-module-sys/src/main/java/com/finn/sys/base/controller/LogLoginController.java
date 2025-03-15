package com.finn.sys.base.controller;

import com.finn.framework.operatelog.annotations.OperateLog;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.sys.base.query.LogLoginQuery;
import com.finn.sys.base.service.LogLoginService;
import com.finn.sys.base.vo.LogLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name = "登录日志")
public class LogLoginController {

    @Resource
    private LogLoginService logLoginService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:log:login')")
    public Result<PageResult<LogLoginVO>> page(@ParameterObject @Valid LogLoginQuery query) {
        PageResult<LogLoginVO> page = logLoginService.page(query);
        return Result.ok(page);
    }

    @GetMapping("export")
    @Operation(summary = "导出excel")
    @OperateLog(module = "登录日志", name = "导出excel", type = OperateTypeEnum.EXPORT)
    @PreAuthorize("hasAuthority('sys:log:login')")
    public void export() {
        logLoginService.export();
    }

}