package com.finn.sys.base.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.sys.base.query.LogOperateQuery;
import com.finn.sys.base.service.LogOperateService;
import com.finn.sys.base.vo.LogOperateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
public class LogOperateController {
    private final LogOperateService logOperateService;

    public LogOperateController(LogOperateService logOperateService) {
        this.logOperateService = logOperateService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:operate:all')")
    public Result<PageResult<LogOperateVO>> page(@ParameterObject @Valid LogOperateQuery query) {
        PageResult<LogOperateVO> page = logOperateService.page(query);

        return Result.ok(page);
    }
}