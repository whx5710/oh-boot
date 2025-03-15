package com.finn.support.controller;

import com.finn.framework.operatelog.annotations.OperateLog;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.support.convert.PostConvert;
import com.finn.support.entity.PostEntity;
import com.finn.support.query.PostQuery;
import com.finn.support.service.PostService;
import com.finn.support.vo.PostVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name = "岗位管理")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:post:page')")
    public Result<PageResult<PostVO>> page(@ParameterObject @Valid PostQuery query) {
        PageResult<PostVO> page = postService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    public Result<List<PostVO>> list() {
        List<PostVO> list = postService.getList();

        return Result.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:post:info')")
    public Result<PostVO> get(@PathVariable("id") Long id) {
        PostEntity entity = postService.getById(id);

        return Result.ok(PostConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(module = "岗位管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:post:save')")
    public Result<String> save(@RequestBody PostVO vo) {
        postService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(module = "岗位管理", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:post:update')")
    public Result<String> update(@RequestBody @Valid PostVO vo) {
        postService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(module = "岗位管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:post:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        postService.delete(idList);

        return Result.ok();
    }
}