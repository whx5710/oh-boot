package com.finn.support.controller;

import com.finn.framework.operatelog.annotations.Log;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.support.convert.PostConvert;
import com.finn.support.entity.PostEntity;
import com.finn.support.query.PostQuery;
import com.finn.support.service.PostService;
import com.finn.support.vo.PostVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 岗位管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("sys/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * 分页
     * @param query 查询条件
     * @return 岗位列表
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:post:page')")
    public Result<PageResult<PostVO>> page(@Valid PostQuery query) {
        PageResult<PostVO> page = postService.page(query);

        return Result.ok(page);
    }

    /**
     * 岗位列表-不分页
     * @return 岗位列表
     */
    @GetMapping("list")
    public Result<List<PostVO>> list() {
        List<PostVO> list = postService.getList();

        return Result.ok(list);
    }

    /**
     * 岗位信息
     * @param id 岗位ID
     * @return 岗位信息
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:post:info')")
    public Result<PostVO> get(@PathVariable("id") Long id) {
        PostEntity entity = postService.getById(id);

        return Result.ok(PostConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存岗位
     * @param vo 岗位信息
     * @return 提示信息
     */
    @PostMapping
    @Log(module = "岗位管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:post:save')")
    public Result<String> save(@RequestBody PostVO vo) {
        postService.save(vo);

        return Result.ok();
    }

    /**
     * 修改岗位
     * @param vo 岗位信息
     * @return 提示信息
     */
    @PutMapping
    @Log(module = "岗位管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:post:update')")
    public Result<String> update(@RequestBody @Valid PostVO vo) {
        postService.update(vo);

        return Result.ok();
    }

    /**
     * 删除岗位
     * @param idList 岗位ID集合
     * @return 提示信息
     */
    @DeleteMapping
    @Log(module = "岗位管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:post:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        postService.delete(idList);

        return Result.ok();
    }
}