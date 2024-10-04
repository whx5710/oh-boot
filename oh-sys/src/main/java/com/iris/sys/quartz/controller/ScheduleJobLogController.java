package com.iris.sys.quartz.controller;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.common.utils.Result;
import com.iris.framework.operatelog.annotations.OperateLog;
import com.iris.framework.operatelog.enums.OperateTypeEnum;
import com.iris.sys.quartz.convert.ScheduleJobLogConvert;
import com.iris.sys.quartz.entity.ScheduleJobLogEntity;
import com.iris.sys.quartz.query.ScheduleJobLogQuery;
import com.iris.sys.quartz.service.ScheduleJobLogService;
import com.iris.sys.quartz.vo.ScheduleJobLogVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name = "定时任务日志")
public class ScheduleJobLogController {
    private final ScheduleJobLogService scheduleJobLogService;

    public ScheduleJobLogController(ScheduleJobLogService scheduleJobLogService) {
        this.scheduleJobLogService = scheduleJobLogService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('schedule:log')")
    public Result<PageResult<ScheduleJobLogVO>> page(@ParameterObject @Valid ScheduleJobLogQuery query) {
        PageResult<ScheduleJobLogVO> page = scheduleJobLogService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('schedule:log')")
    public Result<ScheduleJobLogVO> get(@PathVariable("id") Long id) {
        ScheduleJobLogEntity entity = scheduleJobLogService.getById(id);

        return Result.ok(ScheduleJobLogConvert.INSTANCE.convert(entity));
    }

    @DeleteMapping
    @Operation(summary = "删除日志")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('schedule:log')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        scheduleJobLogService.delete(idList);
        return Result.ok();
    }

}