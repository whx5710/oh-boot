package com.finn.system.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.system.convert.VersionInfoConvert;
import com.finn.system.entity.VersionInfoEntity;
import com.finn.system.query.VersionInfoQuery;
import com.finn.system.service.VersionInfoService;
import com.finn.system.vo.VersionInfoVO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 版本信息
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-09-16
*/
@RestController
@RequestMapping("sys/info")
public class VersionInfoController {

    @Resource
    private VersionInfoService versionInfoService;

    /**
     * 分页
     * @param query
     * @return
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('system:info:page')")
    public Result<PageResult<VersionInfoVO>> page(@Valid VersionInfoQuery query){
        PageResult<VersionInfoVO> page = versionInfoService.page(query);

        return Result.ok(page);
    }

    /**
     * 根据ID获取
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('system:info:info')")
    public Result<VersionInfoVO> get(@PathVariable("id") Long id){
        VersionInfoEntity entity = versionInfoService.getById(id);

        return Result.ok(VersionInfoConvert.INSTANCE.convert(entity));
    }

    /**
     * 最新版本信息
     * @return
     */
    @GetMapping("latestVersion")
    public Result<VersionInfoVO> latestVersion(){
        VersionInfoEntity entity = versionInfoService.latestVersion();
        return Result.ok(VersionInfoConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存
     * @param vo
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('system:info:save')")
    public Result<String> save(@RequestBody VersionInfoVO vo){
        versionInfoService.save(vo);

        return Result.ok();
    }

    /**
     * 修改
     * @param vo
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('system:info:update')")
    public Result<String> update(@RequestBody @Valid VersionInfoVO vo){
        versionInfoService.update(vo);
        return Result.ok();
    }

    /**
     * 删除
     * @param idList
     * @return
     */
    @PostMapping("/del")
    @PreAuthorize("hasAuthority('system:info:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        versionInfoService.delete(idList);
        return Result.ok();
    }
}