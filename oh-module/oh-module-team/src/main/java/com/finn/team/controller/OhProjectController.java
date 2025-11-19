package com.finn.team.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.team.convert.OhProjectConvert;
import com.finn.team.entity.OhProjectEntity;
import com.finn.team.query.OhProjectQuery;
import com.finn.team.service.OhProjectService;
import com.finn.team.vo.OhProjectVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 项目信息表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@RestController
@RequestMapping("team/project")
public class OhProjectController {
    private final OhProjectService ohProjectService;

    public OhProjectController(OhProjectService ohProjectService) {
        this.ohProjectService = ohProjectService;
    }

    /**
     * 分页
     * @param query
     * @return
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('team:project:page')")
    public Result<PageResult<OhProjectVO>> page(@Valid OhProjectQuery query){
        PageResult<OhProjectVO> page = ohProjectService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('team:project:info')")
    public Result<OhProjectVO> get(@PathVariable("id") Long id){
        OhProjectEntity entity = ohProjectService.getById(id);

        return Result.ok(OhProjectConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存
     * @param vo
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('team:project:save')")
    public Result<String> save(@RequestBody OhProjectVO vo){
        vo.setDbStatus(1);
        ohProjectService.save(vo);

        return Result.ok();
    }

    /**
     * 修改
     * @param vo
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('team:project:update')")
    public Result<String> update(@RequestBody @Valid OhProjectVO vo){
        ohProjectService.update(vo);

        return Result.ok();
    }

    /**
     * 删除
     * @param idList
     * @return
     */
    @PostMapping("/del")
    @PreAuthorize("hasAuthority('team:project:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        ohProjectService.delete(idList);

        return Result.ok();
    }
}