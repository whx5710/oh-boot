package com.finn.app.controller;

import com.finn.app.convert.DataFunctionConvert;
import com.finn.app.entity.DataFunctionEntity;
import com.finn.app.query.DataFunctionQuery;
import com.finn.app.service.DataFunctionService;
import com.finn.app.vo.DataFunctionVO;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 功能列表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-30
*/
@RestController
@RequestMapping("/sys/function")
public class DataFunctionController {

    private final DataFunctionService dataFunctionService;

    public DataFunctionController(DataFunctionService dataFunctionService) {
        this.dataFunctionService = dataFunctionService;
    }

    /**
     * 分页
     * @param query
     * @return
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:function:page')")
    public Result<PageResult<DataFunctionVO>> page(@Valid DataFunctionQuery query){
        PageResult<DataFunctionVO> page = dataFunctionService.page(query);
        return Result.ok(page);
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:function:info')")
    public Result<DataFunctionVO> get(@PathVariable("id") Long id){
        DataFunctionEntity entity = dataFunctionService.getById(id);

        return Result.ok(DataFunctionConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存
     * @param vo
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:function:save')")
    public Result<String> save(@RequestBody DataFunctionVO vo){
        dataFunctionService.save(vo);

        return Result.ok();
    }

    /**
     * 修改
     * @param vo
     * @return
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:function:update')")
    public Result<String> update(@RequestBody @Valid DataFunctionVO vo){
        dataFunctionService.update(vo);

        return Result.ok();
    }

    /**
     * 删除
     * @param idList
     * @return
     */
    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:function:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        dataFunctionService.delete(idList);

        return Result.ok();
    }

    /**
     * 根据客户端获取分页数据
     * @param query
     * @return
     */
    @GetMapping("pageByClientId")
    @PreAuthorize("hasAuthority('sys:function:page')")
    public Result<PageResult<DataFunctionVO>> pageByClientId(@Valid DataFunctionQuery query){
        PageResult<DataFunctionVO> page = dataFunctionService.pageByClientId(query);
        return Result.ok(page);
    }
}