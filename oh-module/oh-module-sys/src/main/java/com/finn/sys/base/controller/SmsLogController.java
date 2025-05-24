package com.finn.sys.base.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.sys.base.convert.SmsLogConvert;
import com.finn.sys.base.entity.SmsLogEntity;
import com.finn.sys.base.query.SmsLogQuery;
import com.finn.sys.base.service.SmsLogService;
import com.finn.sys.base.vo.SmsLogVO;
import jakarta.validation.Valid;
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
public class SmsLogController {
    private final SmsLogService smsLogService;

    public SmsLogController(SmsLogService smsLogService) {
        this.smsLogService = smsLogService;
    }

    /**
     * 分页
     * @param query 查询条件
     * @return
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sms:log')")
    public Result<PageResult<SmsLogVO>> page(@Valid SmsLogQuery query) {
        PageResult<SmsLogVO> page = smsLogService.page(query);

        return Result.ok(page);
    }

    /**
     * 根据ID查询短信日志
     * @param id 日志ID
     * @return 数据
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sms:log')")
    public Result<SmsLogVO> get(@PathVariable("id") Long id) {
        SmsLogEntity entity = smsLogService.getById(id);

        return Result.ok(SmsLogConvert.INSTANCE.convert(entity));
    }

}