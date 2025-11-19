package com.finn.support.controller;

import com.finn.framework.operatelog.annotations.Log;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.constant.Constant;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.support.convert.DeptConvert;
import com.finn.support.entity.DeptEntity;
import com.finn.support.query.DeptQuery;
import com.finn.support.service.DeptService;
import com.finn.support.vo.DeptVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("sys/dept")
public class DeptController {
    private final DeptService deptService;

    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    /**
     * 树形列表
     * @param query 部门查询条件
     * @return 树形集合
     */
    @PostMapping("list")
    @PreAuthorize("hasAuthority('sys:dept:list')")
    public Result<List<DeptVO>> list(@RequestBody DeptQuery query) {
        List<DeptVO> list = deptService.getList(query);
        return Result.ok(list);
    }

    /**
     * 分页
     * @param query 查询条件
     * @return 集合
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:dept:list')")
    public Result<PageResult<DeptVO>> page(@Valid DeptQuery query) {
        PageResult<DeptVO> page = deptService.page(query);
        return Result.ok(page);
    }

    /**
     * 根据ID获取部门信息
     * @param id 部门ID
     * @return 部门信息
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:dept:info')")
    public Result<DeptVO> get(@PathVariable("id") Long id) {
        DeptEntity entity = deptService.getById(id);
        DeptVO vo = DeptConvert.INSTANCE.convert(entity);

        // 获取上级部门名称
        if (!Constant.ROOT.equals(entity.getParentId())) {
            DeptEntity parentEntity = deptService.getById(entity.getParentId());
            vo.setParentName(parentEntity.getName());
        }

        return Result.ok(vo);
    }

    /**
     * 保存
     * @param vo 部门信息
     * @return 提示信息
     */
    @PostMapping
    @Log(module = "部门管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:dept:save')")
    public Result<String> save(@RequestBody @Valid DeptVO vo) {
        deptService.save(vo);

        return Result.ok();
    }

    /**
     * 修改部门
     * @param vo 部门信息
     * @return 提示信息
     */
    @PostMapping("/update")
    @Log(module = "部门管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:dept:update')")
    public Result<String> update(@RequestBody @Valid DeptVO vo) {
        deptService.update(vo);

        return Result.ok();
    }

    /**
     * 根据ID删除部门
     * @param id 部门id
     * @return 提示信息
     */
    @PostMapping("/delById/{id}")
    @Log(module = "部门管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:dept:delete')")
    public Result<String> delete(@PathVariable("id") Long id) {
        deptService.delete(id);

        return Result.ok();
    }

}