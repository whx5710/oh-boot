package com.finn.system.controller;

import com.finn.framework.entity.PageResult;
import com.finn.framework.entity.Result;
import com.finn.framework.aop.annotations.Log;
import com.finn.framework.common.enums.OperateTypeEnum;
import com.finn.system.query.OperateLogQuery;
import com.finn.system.service.OperateLogService;
import com.finn.system.vo.OperateLogVO;
import jakarta.validation.Valid;
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
@RequestMapping("/sys/log/operate")
public class OperateLogController {
    private final OperateLogService operateLogService;

    public OperateLogController(OperateLogService operateLogService) {
        this.operateLogService = operateLogService;
    }

    /**
     * 分页
     * @param query 查询条件
     * @return 结合
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:operate:all')")
    public Result<PageResult<OperateLogVO>> page(@Valid OperateLogQuery query) {
        PageResult<OperateLogVO> page = operateLogService.page(query);
        return Result.ok(page);
    }

    /**
     * 导出excel
     */
    @GetMapping("/export")
    @Log(module = "操作日志", name = "导出操作日志", type = OperateTypeEnum.EXPORT)
    @PreAuthorize("hasAuthority('sys:operate:all')")
    public void export(OperateLogQuery query) {
        operateLogService.export(query);
    }
}