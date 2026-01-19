package com.finn.system.controller;

import com.finn.framework.operatelog.annotations.Log;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.system.convert.ScheduleJobLogConvert;
import com.finn.system.entity.ScheduleJobLogEntity;
import com.finn.system.query.ScheduleJobLogQuery;
import com.finn.system.service.ScheduleJobLogService;
import com.finn.system.vo.ScheduleJobLogVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 定时任务日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("schedule/log")
public class ScheduleJobLogController {
    private final ScheduleJobLogService scheduleJobLogService;

    public ScheduleJobLogController(ScheduleJobLogService scheduleJobLogService) {
        this.scheduleJobLogService = scheduleJobLogService;
    }

    /**
     * 分页
     * @param query 查询参数
     * @return 列表
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('schedule:log')")
    public Result<PageResult<ScheduleJobLogVO>> page(@Valid ScheduleJobLogQuery query) {
        PageResult<ScheduleJobLogVO> page = scheduleJobLogService.page(query);

        return Result.ok(page);
    }

    /**
     * 根据ID获取定时任务日志
     * @param id 日志ID
     * @return 日志信息
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('schedule:log')")
    public Result<ScheduleJobLogVO> get(@PathVariable("id") Long id) {
        ScheduleJobLogEntity entity = scheduleJobLogService.getById(id);

        return Result.ok(ScheduleJobLogConvert.INSTANCE.convert(entity));
    }

    /**
     * 删除日志
     * @param idList 日志ID集合
     * @return 提示信息
     */
    @PostMapping("/del")
    @Log(module = "定时任务", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('schedule:log')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        scheduleJobLogService.delete(idList);
        return Result.ok();
    }

}