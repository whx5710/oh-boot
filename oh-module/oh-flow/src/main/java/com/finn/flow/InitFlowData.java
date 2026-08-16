package com.finn.flow;

import com.finn.flow.convert.FlowNodeConvert;
import com.finn.flow.entity.FlowNodeEntity;
import com.finn.flow.mapper.FlowNodeMapper;
import com.finn.flow.vo.FlowNodeVO;
import com.finn.framework.cache.RedisCache;
import com.finn.framework.cache.RedisKeys;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 缓存数据
 */
@Component
public class InitFlowData {

    private final FlowNodeMapper flowNodeMapper;

    private final RedisCache redisCache;

    public InitFlowData(FlowNodeMapper flowNodeMapper, RedisCache redisCache){
        this.flowNodeMapper = flowNodeMapper;
        this.redisCache = redisCache;
    }

    @PostConstruct
    public void run(){
        List<FlowNodeEntity> list = flowNodeMapper.listByWrapper(QueryWrapper.of(FlowNodeEntity.class).eq(FlowNodeEntity::getDbStatus, 1));
        if(list != null && !list.isEmpty()){
            for(FlowNodeEntity item: list){
                FlowNodeVO vo = FlowNodeConvert.INSTANCE.convert(item);
                String key = RedisKeys.PREFIX + "flow:";
                redisCache.set(key + vo.getProcDefId() + ":" + vo.getActDefId(), vo);
            }
        }
        System.out.println("==============================导入了工作流模块===============================");
    }
}
