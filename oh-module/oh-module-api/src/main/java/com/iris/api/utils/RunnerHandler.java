package com.iris.api.utils;

import com.iris.api.service.DataAppService;
import com.iris.api.vo.DataAppVO;
import com.iris.framework.common.cache.RedisCache;
import com.iris.framework.common.cache.RedisKeys;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 缓存数据
 */
@Component
public class RunnerHandler {

    @Resource
    DataAppService dataAppService;

    @Resource
    RedisCache redisCache;

    /**
     * 缓存客户端、接口授权等信息
     */
    @PostConstruct
    public void appAuthority(){
        // 先清除
        redisCache.deleteAll(RedisKeys.getClientKey(""));
        // 客户端权限信息
        List<DataAppVO> list = dataAppService.listAuthority(null);
        if(!ObjectUtils.isEmpty(list)){
            // 根据客户端ID+ funcCode 进行分组
            Map<String,List<DataAppVO>> map = list.stream().collect(
                    Collectors.groupingBy(item -> item.getClientId() + ":" + item.getFuncCode())
            );
            for (Map.Entry<String, List<DataAppVO>> entry : map.entrySet()) {
                redisCache.set(RedisKeys.getClientKey(entry.getKey()), entry.getValue(), RedisCache.NOT_EXPIRE);
            }
        }
    }
}
