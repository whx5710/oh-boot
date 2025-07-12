package com.finn.support.controller;

import com.finn.framework.operatelog.annotations.Log;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.support.convert.DictDataConvert;
import com.finn.support.entity.DictDataEntity;
import com.finn.support.query.DictDataQuery;
import com.finn.support.service.DictDataService;
import com.finn.support.vo.DictDataVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典数据
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("sys/dict/data")
public class DictDataController {
    private final DictDataService dictDataService;

    public DictDataController(DictDataService dictDataService) {
        this.dictDataService = dictDataService;
    }

    /**
     * 分页
     * @param query
     * @return
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<PageResult<DictDataVO>> page(@Valid DictDataQuery query) {
        PageResult<DictDataVO> page = dictDataService.page(query);

        return Result.ok(page);
    }

    /**
     * 根据ID获取字典数据
     * @param id 字典数据ID
     * @return 字典数据
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:dict:info')")
    public Result<DictDataVO> get(@PathVariable("id") Long id) {
        DictDataEntity entity = dictDataService.getById(id);

        return Result.ok(DictDataConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存
     * @param vo 字典数据
     * @return 提示信息
     */
    @PostMapping
    @Log(module = "字典数据管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:dict:save')")
    public Result<String> save(@RequestBody @Valid DictDataVO vo) {
        dictDataService.save(vo);

        return Result.ok();
    }

    /**
     * 修改
     * @param vo 字典数据
     * @return 提示信息
     */
    @PutMapping
    @Log(module = "字典数据管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:dict:update')")
    public Result<String> update(@RequestBody @Valid DictDataVO vo) {
        dictDataService.update(vo);

        return Result.ok();
    }

    /**
     * 删除字典数据
     * @param idList 字典ID集合
     * @return 提示信息
     */
    @DeleteMapping
    @Log(module = "字典数据管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        dictDataService.delete(idList);
        return Result.ok();
    }

}