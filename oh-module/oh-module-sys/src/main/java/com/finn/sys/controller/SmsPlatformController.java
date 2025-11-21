package com.finn.sys.controller;

import com.finn.framework.operatelog.annotations.Log;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.ExceptionUtils;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.sys.convert.SmsPlatformConvert;
import com.finn.sys.entity.Sms;
import com.finn.sys.entity.SmsPlatformEntity;
import com.finn.sys.query.SmsPlatformQuery;
import com.finn.sys.service.SmsPlatformService;
import com.finn.sys.vo.SmsPlatformVO;
import com.finn.sys.vo.SmsSendVO;
import com.finn.sys.service.SmsContext;
import com.finn.sys.service.SmsService;
import jakarta.validation.Valid;
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
public class SmsPlatformController {
    private final SmsPlatformService smsPlatformService;
    private final SmsService smsService;

    public SmsPlatformController(SmsPlatformService smsPlatformService, SmsService smsService) {
        this.smsPlatformService = smsPlatformService;
        this.smsService = smsService;
    }

    /**
     * 分页
     * @param query 查询参数
     * @return 集合
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sms:platform:page')")
    public Result<PageResult<SmsPlatformVO>> page(@Valid SmsPlatformQuery query) {
        PageResult<SmsPlatformVO> page = smsPlatformService.page(query);

        return Result.ok(page);
    }

    /**
     * 根据ID查询短信平台信息
     * @param id 短信平台ID
     * @return data
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sms:platform:info')")
    public Result<SmsPlatformVO> get(@PathVariable("id") Long id) {
        SmsPlatformEntity entity = smsPlatformService.getById(id);

        return Result.ok(SmsPlatformConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存
     * @param vo 数据
     * @return 提示信息
     */
    @PostMapping
    @Log(module = "短信平台", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sms:platform:save')")
    public Result<String> save(@RequestBody SmsPlatformVO vo) {
        smsPlatformService.save(vo);

        return Result.ok();
    }

    /**
     * 发送短信
     * @param vo 短信信息
     * @return 提示信息
     */
    @PostMapping("send")
    @Log(module = "短信平台", name = "发送短信", type = OperateTypeEnum.OTHER)
    @PreAuthorize("hasAuthority('sms:platform:update')")
    public Result<String> send(@RequestBody SmsSendVO vo) {
        SmsPlatformEntity entity = smsPlatformService.getById(vo.getId());
        Sms config = SmsPlatformConvert.INSTANCE.convert2(entity);

        // 短信参数
        Map<String, String> params = new LinkedHashMap<>();
        if (vo.getParamValue() != null && !vo.getParamValue().isEmpty()) {
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

    /**
     * 修改
     * @param vo 短信平台数据
     * @return 提示信息
     */
    @PostMapping("/update")
    @Log(module = "短信平台", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sms:platform:update')")
    public Result<String> update(@RequestBody @Valid SmsPlatformVO vo) {
        smsPlatformService.update(vo);

        return Result.ok();
    }

    /**
     * 删除
     * @param idList ID集合
     * @return 提示信息
     */
    @PostMapping("/del")
    @Log(module = "短信平台", name = "删除", type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sms:platform:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        smsPlatformService.delete(idList);

        return Result.ok();
    }
}