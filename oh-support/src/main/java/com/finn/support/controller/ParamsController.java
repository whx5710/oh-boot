package com.finn.support.controller;

import com.finn.framework.operatelog.annotations.OperateLog;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.support.convert.ParamsConvert;
import com.finn.support.entity.ParamsEntity;
import com.finn.support.query.ParamsQuery;
import com.finn.support.service.ParamsService;
import com.finn.support.vo.ParamsVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 参数管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("sys/params")
public class ParamsController {
    private final ParamsService paramsService;

    public ParamsController(ParamsService paramsService) {
        this.paramsService = paramsService;
    }

    /**
     * 分页
     * @param query 查询条件
     * @return 列表
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<PageResult<ParamsVO>> page(@Valid ParamsQuery query) {
        PageResult<ParamsVO> page = paramsService.page(query);
        return Result.ok(page);
    }

    /**
     * 根据参数KEY获取参数
     * @param key
     * @return
     */
    @GetMapping("/getByKey/{key}")
    public Result<String> getByKey(@PathVariable("key") String key){
        return Result.ok(paramsService.getString(key));
    }

    /**
     * 根据key获取值
     * @param keys
     * @return
     */
    @PostMapping("/getByKeys")
    public Result<Map<String, String>> getByKeys(@RequestBody List<String> keys){
        return Result.ok(paramsService.getByKeys(keys));
    }

    /**
     * 根据ID获取参数信息
     * @param id 参数ID
     * @return 参数信息
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<ParamsVO> get(@PathVariable("id") Long id) {
        ParamsEntity entity = paramsService.getById(id);
        return Result.ok(ParamsConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存参数信息
     * @param vo 参数数据
     * @return 提示信息
     */
    @PostMapping
    @OperateLog(module = "参数管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<String> save(@RequestBody ParamsVO vo) {
        paramsService.save(vo);
        return Result.ok();
    }

    /**
     * 修改
     * @param vo 参数信息
     * @return 提示信息
     */
    @PutMapping
    @OperateLog(module = "参数管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<String> update(@RequestBody @Valid ParamsVO vo) {
        paramsService.update(vo);
        return Result.ok();
    }

    /**
     * 删除参数信息
     * @param idList ID列表
     * @return 提示信息
     */
    @DeleteMapping
    @OperateLog(module = "参数管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        paramsService.delete(idList);
        return Result.ok();
    }

    /**
     * 删除参数信息
     * @param id 参数id
     * @return 是否成功
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('sys:params:all')")
    public Result<Boolean> del(@PathVariable("id") Long id) {
        return Result.ok(paramsService.del(id));
    }
}