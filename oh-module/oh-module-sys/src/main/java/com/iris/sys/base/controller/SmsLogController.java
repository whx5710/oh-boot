package com.iris.sys.base.controller;

import com.iris.core.utils.PageResult;
import com.iris.core.utils.Result;
import com.iris.sys.base.convert.SmsLogConvert;
import com.iris.sys.base.entity.SmsLogEntity;
import com.iris.sys.base.query.SmsLogQuery;
import com.iris.sys.base.service.SmsLogService;
import com.iris.sys.base.vo.SmsLogVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("sms/log")
@Tag(name = "短信日志")
public class SmsLogController {
    private final SmsLogService smsLogService;

    public SmsLogController(SmsLogService smsLogService) {
        this.smsLogService = smsLogService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sms:log')")
    public Result<PageResult<SmsLogVO>> page(@ParameterObject @Valid SmsLogQuery query) {
        PageResult<SmsLogVO> page = smsLogService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sms:log')")
    public Result<SmsLogVO> get(@PathVariable("id") Long id) {
        SmsLogEntity entity = smsLogService.getById(id);

        return Result.ok(SmsLogConvert.INSTANCE.convert(entity));
    }

}