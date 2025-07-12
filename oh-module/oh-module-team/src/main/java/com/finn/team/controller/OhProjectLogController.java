package com.finn.team.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.team.convert.OhProjectLogConvert;
import com.finn.team.entity.OhProjectLogEntity;
import com.finn.team.query.OhProjectLogQuery;
import com.finn.team.service.OhProjectLogService;
import com.finn.team.vo.OhProjectLogVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 项目、任务操作日志
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@RestController
@RequestMapping("team/log")
public class OhProjectLogController {
    private final OhProjectLogService ohProjectLogService;

    public OhProjectLogController(OhProjectLogService ohProjectLogService) {
        this.ohProjectLogService = ohProjectLogService;
    }

    /**
     * 分页
     * @param query
     * @return
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('team:log:page')")
    public Result<PageResult<OhProjectLogVO>> page(@Valid OhProjectLogQuery query){
        PageResult<OhProjectLogVO> page = ohProjectLogService.page(query);

        return Result.ok(page);
    }

    /**
     * 获取项目日志
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('team:log:info')")
    public Result<OhProjectLogVO> get(@PathVariable("id") Long id){
        OhProjectLogEntity entity = ohProjectLogService.getById(id);

        return Result.ok(OhProjectLogConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存
     * @param vo
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('team:log:save')")
    public Result<String> save(@RequestBody OhProjectLogVO vo){
        ohProjectLogService.save(vo);

        return Result.ok();
    }

    /**
     * 修改
     * @param vo
     * @return
     */
    @PutMapping
    @PreAuthorize("hasAuthority('team:log:update')")
    public Result<String> update(@RequestBody @Valid OhProjectLogVO vo){
        ohProjectLogService.update(vo);
        return Result.ok();
    }

    /**
     * 删除
     * @param idList
     * @return
     */
    @DeleteMapping
    @PreAuthorize("hasAuthority('team:log:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        ohProjectLogService.delete(idList);
        return Result.ok();
    }
}