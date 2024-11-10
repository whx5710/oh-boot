package com.iris.sys.base.controller;

import com.iris.framework.operatelog.annotations.OperateLog;
import com.iris.framework.operatelog.enums.OperateTypeEnum;
import com.iris.sys.base.query.SysAttachmentQuery;
import com.iris.sys.base.vo.SysAttachmentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.iris.core.utils.PageResult;
import com.iris.core.utils.Result;
import com.iris.sys.base.service.SysAttachmentService;
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
public class SysAttachmentController {
    private final SysAttachmentService sysAttachmentService;

    public SysAttachmentController(SysAttachmentService sysAttachmentService) {
        this.sysAttachmentService = sysAttachmentService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:attachment:page')")
    public Result<PageResult<SysAttachmentVO>> page(@ParameterObject @Valid SysAttachmentQuery query) {
        PageResult<SysAttachmentVO> page = sysAttachmentService.page(query);

        return Result.ok(page);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:attachment:save')")
    public Result<String> save(@RequestBody SysAttachmentVO vo) {
        sysAttachmentService.save(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:attachment:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysAttachmentService.delete(idList);

        return Result.ok();
    }
}