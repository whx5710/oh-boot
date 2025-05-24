package com.finn.support.controller;

import com.finn.framework.operatelog.annotations.OperateLog;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.support.convert.DictTypeConvert;
import com.finn.support.entity.DictTypeEntity;
import com.finn.support.query.DictTypeQuery;
import com.finn.support.service.DictTypeService;
import com.finn.support.vo.DictTypeVO;
import com.finn.support.vo.DictVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典类型
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("sys/dict/type")
public class DictTypeController {
    private final DictTypeService dictTypeService;

    public DictTypeController(DictTypeService dictTypeService) {
        this.dictTypeService = dictTypeService;
    }

    /**
     * 分页
     * @param query 查询条件
     * @return 列表
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<PageResult<DictTypeVO>> page(@Valid DictTypeQuery query) {
        PageResult<DictTypeVO> page = dictTypeService.page(query);

        return Result.ok(page);
    }

    /**
     * 根据ID获取动态SQL生成的字典数据
     * @param id 字典ID
     * @return 数据列表
     */
    @GetMapping("list/sql")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<PageResult<DictVO.DictData>> listSql(Long id) {
        List<DictVO.DictData> list = dictTypeService.getDictSql(id);

        PageResult<DictVO.DictData> page = new PageResult<>(list, list.size());

        return Result.ok(page);
    }

    /**
     * 根据ID获取字典类型信息
     * @param id 字典类型ID
     * @return
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:dict:info')")
    public Result<DictTypeVO> get(@PathVariable("id") Long id) {
        DictTypeEntity entity = dictTypeService.getById(id);

        return Result.ok(DictTypeConvert.INSTANCE.convert(entity));
    }

    /**
     * 字典类型保存
     * @param vo 字典类型信息
     * @return 提示信息
     */
    @PostMapping
    @OperateLog(module = "字典类型管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:dict:save')")
    public Result<String> save(@RequestBody @Valid DictTypeVO vo) {
        dictTypeService.save(vo);

        return Result.ok();
    }

    /**
     * 修改
     * @param vo 字典类型信息
     * @return 提示信息
     */
    @PutMapping
    @OperateLog(module = "字典类型管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:dict:update')")
    public Result<String> update(@RequestBody @Valid DictTypeVO vo) {
        dictTypeService.update(vo);

        return Result.ok();
    }

    /**
     * 删除
     * @param idList 字典类型ID结合
     * @return 提示信息
     */
    @DeleteMapping
    @OperateLog(module = "字典类型管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        dictTypeService.delete(idList);

        return Result.ok();
    }

    /**
     * 全部字典类型数据
     * @return 列表
     */
    @GetMapping("all")
    public Result<List<DictVO>> all() {
        List<DictVO> dictList = dictTypeService.getDictList();

        return Result.ok(dictList);
    }

    /**
     * 刷新字典翻译缓存数据
     * @return 提示信息
     */
    @GetMapping("refreshTransCache")
    @PreAuthorize("hasAuthority('sys:dict:refreshTransCache')")
    public Result<String> refreshTransCache() {
        dictTypeService.refreshTransCache();
        return Result.ok();
    }

}