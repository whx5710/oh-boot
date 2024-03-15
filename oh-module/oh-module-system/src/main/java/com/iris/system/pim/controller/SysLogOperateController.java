package com.iris.system.pim.controller;

import com.iris.system.pim.vo.SysLogOperateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.common.utils.Result;
import com.iris.system.pim.query.SysLogOperateQuery;
import com.iris.system.pim.service.SysLogOperateService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("sys/log/operate")
@Tag(name = "操作日志")
public class SysLogOperateController {
    private final SysLogOperateService sysLogOperateService;

    public SysLogOperateController(SysLogOperateService sysLogOperateService) {
        this.sysLogOperateService = sysLogOperateService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:operate:all')")
    public Result<PageResult<SysLogOperateVO>> page(@ParameterObject @Valid SysLogOperateQuery query) {
        PageResult<SysLogOperateVO> page = sysLogOperateService.page(query);

        return Result.ok(page);
    }
}