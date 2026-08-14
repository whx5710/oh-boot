package com.finn.urban.controller;

import com.finn.common.entity.PageResult;
import com.finn.common.entity.Result;
import com.finn.common.enums.OperateTypeEnum;
import com.finn.framework.aop.annotations.Log;
import com.finn.urban.convert.PoiConvert;
import com.finn.urban.entity.Poi;
import com.finn.urban.query.PoiQuery;
import com.finn.urban.service.PoiService;
import com.finn.urban.vo.PoiVO;
import com.github.pagehelper.Page;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 兴趣点
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-13 15:09:28
 * 
 */
@RestController
@RequestMapping("/poi")
public class PoiController {
    private final PoiService poiService;

    public PoiController(PoiService poiService) {
        this.poiService = poiService;
    }

    /**
     * 分页查询
     * @param query 查询条件
     * @return 列表
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('poi:page')")
    public Result<PageResult<PoiVO>> page(@Valid PoiQuery query) {
        Page<Poi> page = poiService.page(query);
        return Result.ok(new PageResult<>(PoiConvert.INSTANCE.convertList(page.getResult()), page.getTotal()));
    }

    /**
     * 保存
     * @param vo 兴趣点信息
     * @return 提示信息
     */
    @PostMapping("/save")
    @Log(module = "兴趣点", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('poi:save')")
    public Result<String> save(@RequestBody PoiVO vo) {
        Long id = poiService.save(vo);
        return Result.ok(String.valueOf(id));
    }

    /**
     * 修改
     * @param vo 兴趣点信息
     * @return 提示信息
     */
    @PostMapping("/update")
    @Log(module = "兴趣点", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('poi:update')")
    public Result<String> update(@RequestBody PoiVO vo) {
        poiService.update(vo);
        return Result.ok();
    }

    /**
     * 删除
     * @param idList 兴趣点ID集合
     * @return 提示信息
     */
    @PostMapping("/del")
    @Log(module = "兴趣点", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('poi:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        poiService.delete(idList);
        return Result.ok();
    }
}
