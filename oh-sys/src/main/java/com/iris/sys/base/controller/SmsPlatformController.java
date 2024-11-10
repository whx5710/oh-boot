package com.iris.sys.base.controller;

import com.iris.core.utils.ExceptionUtils;
import com.iris.core.utils.PageResult;
import com.iris.core.utils.Result;
import com.iris.framework.operatelog.annotations.OperateLog;
import com.iris.framework.operatelog.enums.OperateTypeEnum;
import com.iris.sys.base.query.SmsPlatformQuery;
import com.iris.sys.base.vo.SmsPlatformVO;
import com.iris.sys.base.vo.SmsSendVO;
import com.iris.sys.sms.config.SmsConfig;
import com.iris.sys.base.convert.SmsPlatformConvert;
import com.iris.sys.base.entity.SmsPlatformEntity;
import com.iris.sys.sms.SmsContext;
import com.iris.sys.base.service.SmsPlatformService;
import com.iris.sys.sms.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信平台
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("sms/platform")
@Tag(name = "短信平台")
public class SmsPlatformController {
    private final SmsPlatformService smsPlatformService;
    private final SmsService smsService;

    public SmsPlatformController(SmsPlatformService smsPlatformService, SmsService smsService) {
        this.smsPlatformService = smsPlatformService;
        this.smsService = smsService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sms:platform:page')")
    public Result<PageResult<SmsPlatformVO>> page(@ParameterObject @Valid SmsPlatformQuery query) {
        PageResult<SmsPlatformVO> page = smsPlatformService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sms:platform:info')")
    public Result<SmsPlatformVO> get(@PathVariable("id") Long id) {
        SmsPlatformEntity entity = smsPlatformService.getById(id);

        return Result.ok(SmsPlatformConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sms:platform:save')")
    public Result<String> save(@RequestBody SmsPlatformVO vo) {
        smsPlatformService.save(vo);

        return Result.ok();
    }

    @PostMapping("send")
    @Operation(summary = "发送短信")
    @OperateLog(type = OperateTypeEnum.OTHER)
    @PreAuthorize("hasAuthority('sms:platform:update')")
    public Result<String> send(@RequestBody SmsSendVO vo) {
        SmsPlatformEntity entity = smsPlatformService.getById(vo.getId());
        SmsConfig config = SmsPlatformConvert.INSTANCE.convert2(entity);

        // 短信参数
        Map<String, String> params = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(vo.getParamValue())) {
            params.put(vo.getParamKey(), vo.getParamValue());
        }

        try {
            // 发送短信
            new SmsContext(config).send(vo.getMobile(), params);

            // 保存日志
            smsService.saveLog(config, vo.getMobile(), params, null);

            return Result.ok();
        } catch (Exception e) {
            // 保存日志
            smsService.saveLog(config, vo.getMobile(), params, e);

            return Result.error(ExceptionUtils.getExceptionMessage(e));
        }
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sms:platform:update')")
    public Result<String> update(@RequestBody @Valid SmsPlatformVO vo) {
        smsPlatformService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sms:platform:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        smsPlatformService.delete(idList);

        return Result.ok();
    }
}