package com.finn.sys.quartz.controller;

import com.finn.framework.operatelog.annotations.Log;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.framework.utils.ServiceFactory;
import com.finn.sys.quartz.convert.ScheduleJobConvert;
import com.finn.sys.quartz.entity.ScheduleJobEntity;
import com.finn.sys.quartz.query.ScheduleJobQuery;
import com.finn.sys.quartz.service.ScheduleJobService;
import com.finn.sys.quartz.utils.CronUtils;
import com.finn.sys.quartz.vo.ScheduleJobVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("schedule")
public class ScheduleJobController {
    private final ScheduleJobService scheduleJobService;

    public ScheduleJobController(ScheduleJobService scheduleJobService) {
        this.scheduleJobService = scheduleJobService;
    }

    /**
     * 分页
     * @param query 查询参数
     * @return 列表
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('schedule:page')")
    public Result<PageResult<ScheduleJobVO>> page(@Valid ScheduleJobQuery query) {
        PageResult<ScheduleJobVO> page = scheduleJobService.page(query);

        return Result.ok(page);
    }

    /**
     * 根据ID获取定时任务
     * @param id ID
     * @return data
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('schedule:info')")
    public Result<ScheduleJobVO> get(@PathVariable("id") Long id) {
        ScheduleJobEntity entity = scheduleJobService.getById(id);

        return Result.ok(ScheduleJobConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存定时任务
     * @param vo 定时任务
     * @return 提示信息
     */
    @PostMapping
    @Log(module = "定时任务", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('schedule:save')")
    public Result<String> save(@RequestBody ScheduleJobVO vo) {
        if (!CronUtils.isValid(vo.getCronExpression())) {
            return Result.error("操作失败，Cron表达式不正确");
        }

        // 检查Bean的合法性
        checkBean(vo.getBeanName());

        scheduleJobService.save(vo);

        return Result.ok();
    }

    /**
     * 修改定时任务
     * @param vo 定时任务信息
     * @return 提示信息
     */
    @PutMapping
    @Log(module = "定时任务", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('schedule:update')")
    public Result<String> update(@RequestBody @Valid ScheduleJobVO vo) {
        if (!CronUtils.isValid(vo.getCronExpression())) {
            return Result.error("操作失败，Cron表达式不正确");
        }

        // 检查Bean的合法性
        checkBean(vo.getBeanName());

        scheduleJobService.update(vo);

        return Result.ok();
    }

    /**
     * 删除定时任务信息
     * @param idList 定时任务ID集合
     * @return 提示信息
     */
    @DeleteMapping
    @Log(module = "定时任务", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('schedule:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        scheduleJobService.delete(idList);

        return Result.ok();
    }

    /**
     * 执行定时任务
     * @param vo 参数
     * @return 提示信息
     */
    @PutMapping("run")
    @Log(module = "定时任务", name = "立即执行", type = OperateTypeEnum.OTHER)
    @PreAuthorize("hasAuthority('schedule:run')")
    public Result<String> run(@RequestBody ScheduleJobVO vo) {
        scheduleJobService.run(vo);

        return Result.ok();
    }

    /**
     * 修改状态
     * @param vo 定时任务
     * @return 提示信息
     */
    @PutMapping("change-status")
    @Log(module = "定时任务", name = "修改状态", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('schedule:update')")
    public Result<String> changeStatus(@RequestBody ScheduleJobVO vo) {
        scheduleJobService.changeStatus(vo);

        return Result.ok();
    }

    private void checkBean(String beanName) {
        // 为避免执行jdbcTemplate等类，只允许添加有@Service注解的Bean
        Map<String, Object> beans = ServiceFactory.getBeansWithAnnotation(Service.class);
        if (!beans.containsKey(beanName)) {
            throw new ServerException("只允许添加有@Service注解的Bean！");
        }
    }
}