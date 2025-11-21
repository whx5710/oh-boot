package com.finn.sys.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.framework.operatelog.annotations.Log;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.sys.query.LogOperateQuery;
import com.finn.sys.service.LogOperateService;
import com.finn.sys.vo.LogOperateVO;
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
@RequestMapping("sys/log/operate")
public class LogOperateController {
    private final LogOperateService logOperateService;

    public LogOperateController(LogOperateService logOperateService) {
        this.logOperateService = logOperateService;
    }

    /**
     * 分页
     * @param query 查询条件
     * @return 结合
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:operate:all')")
    public Result<PageResult<LogOperateVO>> page(@Valid LogOperateQuery query) {
        PageResult<LogOperateVO> page = logOperateService.page(query);
        return Result.ok(page);
    }

    /**
     * 导出excel
     */
    @GetMapping("export")
    @Log(module = "操作日志", name = "导出操作日志", type = OperateTypeEnum.EXPORT)
    @PreAuthorize("hasAuthority('sys:operate:all')")
    public void export(LogOperateQuery query) {
        logOperateService.export(query);
    }
}