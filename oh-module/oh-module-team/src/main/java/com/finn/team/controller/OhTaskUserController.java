package com.finn.team.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.team.convert.OhTaskUserConvert;
import com.finn.team.entity.OhTaskUserEntity;
import com.finn.team.query.OhTaskUserQuery;
import com.finn.team.service.OhTaskUserService;
import com.finn.team.vo.OhTaskUserVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 任务人员表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@RestController
@RequestMapping("team/user")
public class OhTaskUserController {
    private final OhTaskUserService ohTaskUserService;

    public OhTaskUserController(OhTaskUserService ohTaskUserService) {
        this.ohTaskUserService = ohTaskUserService;
    }

    /**
     * 分页查询
     * @param query
     * @return
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('team:user:page')")
    public Result<PageResult<OhTaskUserVO>> page(@Valid OhTaskUserQuery query){
        PageResult<OhTaskUserVO> page = ohTaskUserService.page(query);

        return Result.ok(page);
    }

    /**
     * 查询单个
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('team:user:info')")
    public Result<OhTaskUserVO> get(@PathVariable("id") Long id){
        OhTaskUserEntity entity = ohTaskUserService.getById(id);

        return Result.ok(OhTaskUserConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存
     * @param vo
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('team:user:save')")
    public Result<String> save(@RequestBody OhTaskUserVO vo){
        ohTaskUserService.save(vo);

        return Result.ok();
    }

    /**
     * 修改
     * @param vo
     * @return
     */
    @PutMapping
    @PreAuthorize("hasAuthority('team:user:update')")
    public Result<String> update(@RequestBody @Valid OhTaskUserVO vo){
        ohTaskUserService.update(vo);

        return Result.ok();
    }

    /**
     * 删除
     * @param idList
     * @return
     */
    @DeleteMapping
    @PreAuthorize("hasAuthority('team:user:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        ohTaskUserService.delete(idList);

        return Result.ok();
    }
}