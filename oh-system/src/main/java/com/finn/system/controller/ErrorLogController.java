package com.finn.system.controller;

import com.finn.framework.aop.annotations.Log;
import com.finn.common.enums.OperateTypeEnum;
import com.finn.common.entity.PageResult;
import com.finn.common.entity.Result;
import com.finn.system.query.ErrorLogQuery;
import com.finn.system.service.ErrorLogService;
import com.finn.system.vo.ErrorLogVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统错误日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-04-29 18:38:22
 * 
 */
@RestController
@RequestMapping("/sys/errorLog")
public class ErrorLogController {
    private final ErrorLogService errorLogService;

    public ErrorLogController(ErrorLogService errorLogService) {
        this.errorLogService = errorLogService;
    }

    /**
     * 分页
     * @param query 查询参数
     * @return 集合
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:error:log')")
    public Result<PageResult<ErrorLogVO>> page(@Valid ErrorLogQuery query) {
        return Result.ok(errorLogService.page(query));
    }

    /**
     * 导出excel
     */
    @GetMapping("/export")
    @Log(module = "登录日志", name = "导出登录日志", type = OperateTypeEnum.EXPORT)
    @PreAuthorize("hasAuthority('sys:error:log')")
    public void export(ErrorLogQuery query) {
        errorLogService.export(query);
    }

    /**
     * 删除
     * @param idList id集合
     * @return str
     */
    @PostMapping("/del")
    @PreAuthorize("hasAuthority('sys:error:log:delete')")
    public Result<Long> delete(@RequestBody List<Long> idList){
        return Result.ok(errorLogService.delete(idList));
    }

    /**
     * 根据日期删除日志（删除日期之前的数据）
     * @param date
     * @return
     */
    @GetMapping("/deleteByDate/{date}")
    @PreAuthorize("hasAuthority('sys:error:log:delete')")
    public Result<Long> deleteByDate(@PathVariable String date){
        return Result.ok(errorLogService.deleteByDate(date));
    }
}
