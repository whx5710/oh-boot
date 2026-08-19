package com.finn.urban.controller;

import com.finn.common.entity.PageResult;
import com.finn.common.entity.Result;
import com.finn.urban.convert.SportRecordConvert;
import com.finn.urban.entity.SportRecordEntity;
import com.finn.urban.query.SportRecordQuery;
import com.finn.urban.service.SportRecordService;
import com.finn.urban.vo.SportRecordVO;
import com.github.pagehelper.Page;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 运动记录表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-19
 */
@RestController
@RequestMapping("/sport")
public class SportRecordController {

    private final SportRecordService sportRecordService;

    public SportRecordController(SportRecordService sportRecordService) {
        this.sportRecordService = sportRecordService;
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('urban:sport-record:page')")
    public Result<PageResult<SportRecordVO>> page(@Valid SportRecordQuery query) {
        Page<SportRecordEntity> page = sportRecordService.page(query);
        return Result.ok(SportRecordConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
    }

    /**
     * 根据ID查询详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('urban:sport-record:info')")
    public Result<SportRecordVO> info(@PathVariable("id") Long id) {
        return Result.ok(sportRecordService.detail(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('urban:sport-record:save')")
    public Result<String> save(@RequestBody SportRecordVO vo) {
        Long id = sportRecordService.save(vo);
        return Result.ok(String.valueOf(id));
    }


    /**
     * 新增运动
     */
    @PostMapping("/start")
    // @PreAuthorize("hasAuthority('urban:sport-record:start')")
    public Result<String> start() {
        Long id = sportRecordService.start();
        return Result.ok(String.valueOf(id));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('urban:sport-record:update')")
    public Result<String> update(@RequestBody SportRecordVO vo) {
        sportRecordService.update(vo);
        return Result.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/del")
    @PreAuthorize("hasAuthority('urban:sport-record:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sportRecordService.delete(idList);
        return Result.ok();
    }
}
