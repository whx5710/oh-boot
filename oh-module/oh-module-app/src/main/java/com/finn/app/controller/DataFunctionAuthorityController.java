package com.finn.app.controller;

import com.finn.app.convert.DataFunctionAuthorityConvert;
import com.finn.app.entity.DataFunctionAuthorityEntity;
import com.finn.app.query.DataFunctionAuthorityQuery;
import com.finn.app.service.DataFunctionAuthorityService;
import com.finn.app.vo.DataFunctionAuthorityVO;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 客户端接口授权
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-29
*/
@RestController
@RequestMapping("/sys/authority")
public class DataFunctionAuthorityController {

    private final DataFunctionAuthorityService dataFunctionAuthorityService;

    public DataFunctionAuthorityController(DataFunctionAuthorityService dataFunctionAuthorityService) {
        this.dataFunctionAuthorityService = dataFunctionAuthorityService;
    }

    /**
     * 分页
     * @param query
     * @return
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:authority:page')")
    public Result<PageResult<DataFunctionAuthorityVO>> page(@Valid DataFunctionAuthorityQuery query){
        PageResult<DataFunctionAuthorityVO> page = dataFunctionAuthorityService.page(query);
        return Result.ok(page);
    }

    /**
     * 根据授权ID查询相关信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:authority:info')")
    public Result<DataFunctionAuthorityVO> get(@PathVariable("id") Long id){
        DataFunctionAuthorityEntity entity = dataFunctionAuthorityService.getById(id);
        return Result.ok(DataFunctionAuthorityConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存
     * @param vo
     * @return
     */
    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:authority:save')")
    public Result<String> save(@RequestBody DataFunctionAuthorityVO vo){
        dataFunctionAuthorityService.save(vo);
        return Result.ok();
    }

    /**
     * 修改
     * @param vo
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:authority:update')")
    public Result<String> update(@RequestBody @Valid DataFunctionAuthorityVO vo){
        dataFunctionAuthorityService.update(vo);
        return Result.ok();
    }

    /**
     * 删除
     * @param idList
     * @return
     */
    @PostMapping("/del")
    @PreAuthorize("hasAuthority('sys:authority:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        dataFunctionAuthorityService.delete(idList);
        return Result.ok();
    }

    /**
     * 授权、取消授权
     * @param data
     * @return
     */
    @PostMapping("make")
    public Result<String> make(@RequestBody DataFunctionAuthorityVO data){
        dataFunctionAuthorityService.make(data);
        return Result.ok();
    }
}