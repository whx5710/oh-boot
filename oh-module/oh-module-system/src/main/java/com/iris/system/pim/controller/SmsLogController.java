package com.iris.system.pim.controller;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.common.utils.Result;
import com.iris.system.pim.convert.SmsLogConvert;
import com.iris.system.pim.entity.SmsLogEntity;
import com.iris.system.pim.query.SmsLogQuery;
import com.iris.system.pim.service.SmsLogService;
import com.iris.system.pim.vo.SmsLogVO;
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