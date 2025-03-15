package com.finn.sys.base.controller;

import com.finn.framework.operatelog.annotations.OperateLog;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.sys.base.query.AttachmentQuery;
import com.finn.sys.base.service.AttachmentService;
import com.finn.sys.base.vo.AttachmentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("sys/attachment")
@Tag(name = "附件管理")
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:attachment:page')")
    public Result<PageResult<AttachmentVO>> page(@ParameterObject @Valid AttachmentQuery query) {
        PageResult<AttachmentVO> page = attachmentService.page(query);

        return Result.ok(page);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(module = "附件管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:attachment:save')")
    public Result<String> save(@RequestBody AttachmentVO vo) {
        attachmentService.save(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(module = "附件管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:attachment:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        attachmentService.delete(idList);

        return Result.ok();
    }
}