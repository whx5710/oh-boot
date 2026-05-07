package com.finn.framework.cache;

import com.finn.framework.common.properties.CommonProperty;
import com.finn.framework.entity.HashDto;
import com.finn.framework.entity.Result;
import com.finn.framework.exception.TraceIdUtils;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.finn.framework.common.constant.CommConstant.ERROR_LOG_KEY;

/**
 * 认证 Cache
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Component
public class ErrorLogCache {

    private final Logger log = LoggerFactory.getLogger(ErrorLogCache.class);

    private final RedisCache redisCache;
    private final CommonProperty commonProperty;

    public ErrorLogCache(RedisCache redisCache, CommonProperty commonProperty){
        this.redisCache = redisCache;
        this.commonProperty = commonProperty;
    }

    /**
     * 缓存日志,缓存时间不要太长，防止恶意攻击导致redis内存溢出
     * @param result 响应结果
     * @param ex 异常
     */
    public void cacheLog(Result<String> result, Exception ex){
        // 默认缓存5分钟，缓存时间要与消费总时间相匹配，如果消费时间比缓存时间长，那么就会造成缓存的错误日志丢失
        if(commonProperty.getErrLogCache()){
            Long size = redisCache.getListSize(ERROR_LOG_KEY);
            if(size < commonProperty.getLogCacheMaxSize()){
                String msg = ex.getMessage().strip();
                HashDto dto = new HashDto();
                dto.put("stackInfo", msg);
                dto.put("errTime", LocalDateTime.now());
                dto.put("errCode", result.getCode());
                dto.put("msg", result.getMsg());
                dto.put("traceId", TraceIdUtils.getTraceId()); // 链路跟踪ID
                dto.put("queueSize", size); // 队列大小
                // 获取租户编码
                UserDetail user = SecurityUser.getUser();
                if(user != null && user.getTenantId() != null && !user.getTenantId().isEmpty()){
                    dto.put("tenantId", user.getTenantId());
                }
                redisCache.leftPush(ERROR_LOG_KEY, dto.toJson(), commonProperty.getLogCacheTime());
            }else{
                log.warn("错误日志缓存超出最大数量！{}", commonProperty.getLogCacheMaxSize());
            }
        }
    }

    /**
     * 获取日志列表
     * @param num 获取的数量
     * @return 日志列表，返回{data: 日志json串, cacheSize: 剩余日志缓存大小}
     */
    public List<HashDto> getLog(int num){
        List<HashDto> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Object object = redisCache.rightPop(ERROR_LOG_KEY);
            if(object == null){
                break;
            }
            HashDto hashDto = new HashDto();
            hashDto.put("data", object.toString());
            hashDto.put("cacheSize", Math.toIntExact(redisCache.getListSize(ERROR_LOG_KEY)));
            list.add(hashDto);
        }
        return list;
    }
}
