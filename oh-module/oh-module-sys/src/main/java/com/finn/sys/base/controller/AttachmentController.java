package com.finn.sys.base.controller;

import com.finn.framework.operatelog.annotations.Log;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.sys.base.query.AttachmentQuery;
import com.finn.sys.base.service.AttachmentService;
import com.finn.sys.base.vo.AttachmentVO;
import jakarta.validation.Valid;
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
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    /**
     * 分页查询
     * @param query 查询条件
     * @return 列表
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:attachment:page')")
    public Result<PageResult<AttachmentVO>> page(@Valid AttachmentQuery query) {
        PageResult<AttachmentVO> page = attachmentService.page(query);

        return Result.ok(page);
    }

    /**
     * 保存
     * @param vo 附件信息
     * @return 提示信息
     */
    @PostMapping
    @Log(module = "附件管理", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:attachment:save')")
    public Result<String> save(@RequestBody AttachmentVO vo) {
        Long id = attachmentService.save(vo);
        return Result.ok(String.valueOf(id));
    }

    /**
     * 删除
     * @param idList 附件ID集合
     * @return 提示信息
     */
    @DeleteMapping
    @Log(module = "附件管理", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:attachment:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        attachmentService.delete(idList);

        return Result.ok();
    }
}