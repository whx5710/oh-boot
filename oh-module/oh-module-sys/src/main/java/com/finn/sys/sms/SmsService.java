package com.finn.sys.sms;

import com.finn.core.constant.Constant;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.ExceptionUtils;
import com.finn.core.utils.JsonUtils;
import com.finn.sys.base.cache.SmsPlatformCache;
import com.finn.sys.base.entity.SmsLogEntity;
import com.finn.sys.base.service.SmsLogService;
import com.finn.sys.base.service.SmsPlatformService;
import com.finn.sys.sms.config.SmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信服务
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SmsService {
    private final SmsPlatformService smsPlatformService;
    private final SmsLogService smsLogService;
    private final SmsPlatformCache smsCacheService;

    private final Logger log = LoggerFactory.getLogger(SmsService.class);

    public SmsService(SmsPlatformService smsPlatformService, SmsLogService smsLogService, SmsPlatformCache smsCacheService) {
        this.smsPlatformService = smsPlatformService;
        this.smsLogService = smsLogService;
        this.smsCacheService = smsCacheService;
    }

    /**
     * 发送短信
     * @param mobile 手机号
     * @return  是否发送成功
     */
    public boolean send(String mobile){
        return this.send(mobile, new HashMap<>());
    }

    /**
     * 发送短信
     * @param mobile 手机号
     * @param params 参数
     * @return  是否发送成功
     */
    public boolean send(String mobile, Map<String, String> params){
        SmsConfig config = roundSmsConfig();;
        try {
            // 发送短信
            new SmsContext(config).send(mobile, params);

            saveLog(config, mobile, params, null);
            return true;
        }catch (Exception e) {
            log.error("短信发送失败，手机号：{}", mobile, e);

            saveLog(config, mobile, params, e);

            return false;
        }
    }

    /**
     * 保存短信日志
     */
    public void saveLog(SmsConfig config, String mobile, Map<String, String> params, Exception e) {
        SmsLogEntity logEntity = new SmsLogEntity();
        logEntity.setPlatform(config.getPlatform());
        logEntity.setPlatformId(config.getId());
        logEntity.setMobile(mobile);
        logEntity.setParams(JsonUtils.toJsonString(params));

        if(e != null) {
            String error = ExceptionUtils.getExceptionMessage(e).substring(0, 2000);
            logEntity.setStatus(Constant.FAIL);
            logEntity.setError(error);
        }else {
            logEntity.setStatus(Constant.SUCCESS);
        }

        smsLogService.save(logEntity);
    }

    /**
     *  通过轮询算法，获取短信平台的配置
     */
    private SmsConfig roundSmsConfig() {
        List<SmsConfig> platformList = smsPlatformService.listByEnable();

        // 是否有可用的短信平台
        int count = platformList.size();
        if(count == 0) {
            throw new ServerException("没有可用的短信平台，请先添加");
        }

        // 采用轮询算法，发送短信
        long round = smsCacheService.getRoundValue();

        return platformList.get((int)round % count);
    }

}