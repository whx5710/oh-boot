package com.finn.sys.base.controller;

import com.finn.framework.operatelog.annotations.Log;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.support.query.LogLoginQuery;
import com.finn.support.service.LogLoginService;
import com.finn.support.vo.LogLoginVO;
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
public class LogLoginController {

    private final LogLoginService logLoginService;

    public LogLoginController(LogLoginService logLoginService){
        this.logLoginService = logLoginService;
    }

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
    @Log(module = "登录日志", name = "导出登录日志", type = OperateTypeEnum.EXPORT)
    @PreAuthorize("hasAuthority('sys:log:login')")
    public void export(LogLoginQuery query) {
        logLoginService.export(query);
    }

    /**
     * 删除
     * @param idList id集合
     * @return str
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('sys:log:login:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        logLoginService.delete(idList);
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
        logLoginService.deleteByDate(date);
        return Result.ok();
    }

}