package com.finn.urban.controller;

import com.finn.common.entity.PageResult;
import com.finn.common.entity.Result;
import com.finn.common.enums.OperateTypeEnum;
import com.finn.framework.aop.annotations.Log;
import com.finn.urban.convert.TrajectoryConvert;
import com.finn.urban.entity.TrajectoryEntity;
import com.finn.urban.query.TrajectoryQuery;
import com.finn.urban.service.TrajectoryService;
import com.finn.urban.vo.TrajectoryVO;
import com.github.pagehelper.Page;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轨迹坐标表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-17
 */
@RestController
@RequestMapping("/ur/trajectory")
public class TrajectoryController {

    private final TrajectoryService trajectoryService;

    public TrajectoryController(TrajectoryService trajectoryService) {
        this.trajectoryService = trajectoryService;
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('urban:trajectory:page')")
    public Result<PageResult<TrajectoryVO>> page(@Valid TrajectoryQuery query) {
        Page<TrajectoryEntity> page = trajectoryService.page(query);
        return Result.ok(TrajectoryConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
    }

    /**
     * 根据ID查询详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('urban:trajectory:info')")
    public Result<TrajectoryVO> info(@PathVariable("id") Long id) {
        return Result.ok(trajectoryService.detail(id));
    }

    /**
     * 批量保存
     */
    @PostMapping("/save")
    @Log(module = "轨迹坐标", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('urban:trajectory:save')")
    public Result<String> save(@RequestBody List<TrajectoryVO> list) {
        trajectoryService.saveBatch(list);
        return Result.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Log(module = "轨迹坐标", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('urban:trajectory:update')")
    public Result<String> update(@RequestBody TrajectoryVO vo) {
        trajectoryService.update(vo);
        return Result.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/del")
    @Log(module = "轨迹坐标", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('urban:trajectory:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        trajectoryService.delete(idList);
        return Result.ok();
    }
}
