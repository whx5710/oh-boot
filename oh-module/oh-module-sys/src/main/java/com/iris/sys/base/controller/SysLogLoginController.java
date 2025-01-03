package com.iris.sys.base.controller;

import com.iris.framework.operatelog.annotations.OperateLog;
import com.iris.framework.operatelog.enums.OperateTypeEnum;
import com.iris.core.utils.PageResult;
import com.iris.core.utils.Result;
import com.iris.sys.base.query.SysLogLoginQuery;
import com.iris.sys.base.service.SysLogLoginService;
import com.iris.sys.base.vo.SysLogLoginVO;
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
public class SysLogLoginController {

    @Resource
    private SysLogLoginService sysLogLoginService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:log:login')")
    public Result<PageResult<SysLogLoginVO>> page(@ParameterObject @Valid SysLogLoginQuery query) {
        PageResult<SysLogLoginVO> page = sysLogLoginService.page(query);
        return Result.ok(page);
    }

    @GetMapping("export")
    @Operation(summary = "导出excel")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    @PreAuthorize("hasAuthority('sys:log:login')")
    public void export() {
        sysLogLoginService.export();
    }

}