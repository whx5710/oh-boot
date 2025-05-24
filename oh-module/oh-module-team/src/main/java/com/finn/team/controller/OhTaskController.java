package com.finn.team.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.team.convert.OhTaskConvert;
import com.finn.team.entity.OhTaskEntity;
import com.finn.team.query.OhTaskQuery;
import com.finn.team.service.OhTaskService;
import com.finn.team.vo.OhTaskVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 任务表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@RestController
@RequestMapping("team/task")
public class OhTaskController {
    private final OhTaskService ohTaskService;

    public OhTaskController(OhTaskService ohTaskService) {
        this.ohTaskService = ohTaskService;
    }

    /**
     * 分页
     * @param query
     * @return
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('team:task:page')")
    public Result<PageResult<OhTaskVO>> page(@Valid OhTaskQuery query){
        PageResult<OhTaskVO> page = ohTaskService.page(query);

        return Result.ok(page);
    }

    /**
     * 获取单个
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('team:task:info')")
    public Result<OhTaskVO> get(@PathVariable("id") Long id){
        OhTaskEntity entity = ohTaskService.getById(id);

        return Result.ok(OhTaskConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存
     * @param vo
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('team:task:save')")
    public Result<String> save(@RequestBody OhTaskVO vo){
        ohTaskService.save(vo);

        return Result.ok();
    }

    /**
     * 修改
     * @param vo
     * @return
     */
    @PutMapping
    @PreAuthorize("hasAuthority('team:task:update')")
    public Result<String> update(@RequestBody @Valid OhTaskVO vo){
        ohTaskService.update(vo);

        return Result.ok();
    }

    /**
     * 删除
     * @param idList
     * @return
     */
    @DeleteMapping
    @PreAuthorize("hasAuthority('team:task:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        ohTaskService.delete(idList);

        return Result.ok();
    }
}