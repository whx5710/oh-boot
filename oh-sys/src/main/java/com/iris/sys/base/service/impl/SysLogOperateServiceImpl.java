package com.iris.sys.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.sys.base.mapper.SysLogOperateMapper;
import com.iris.sys.base.query.SysLogOperateQuery;
import com.iris.sys.base.vo.SysLogOperateVO;
import com.iris.sys.base.service.SysLogOperateService;
import jakarta.annotation.PostConstruct;
import com.iris.framework.cache.RedisCache;
import com.iris.framework.cache.RedisKeys;
import com.iris.framework.common.utils.ExceptionUtils;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.operatelog.dto.OperateLogDTO;
import com.iris.sys.base.convert.SysLogOperateConvert;
import com.iris.sys.base.entity.SysLogOperateEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SysLogOperateServiceImpl implements SysLogOperateService {

    private final static Logger log = LoggerFactory.getLogger(SysLogOperateServiceImpl.class);

    private final RedisCache redisCache;
    private final SysLogOperateMapper sysLogOperateMapper;

    public SysLogOperateServiceImpl(RedisCache redisCache, SysLogOperateMapper sysLogOperateMapper) {
        this.redisCache = redisCache;
        this.sysLogOperateMapper = sysLogOperateMapper;
    }

    @Override
    public PageResult<SysLogOperateVO> page(SysLogOperateQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SysLogOperateEntity> list = sysLogOperateMapper.getList(query);
        PageInfo<SysLogOperateEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysLogOperateConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    /**
     * 启动项目时，从Redis队列获取操作日志并保存
     */
    @PostConstruct
    public void saveLog() {
        ScheduledExecutorService scheduledService = ThreadUtil.createScheduledExecutor(1);

        // 每隔10秒钟，执行一次
        scheduledService.scheduleWithFixedDelay(() -> {
            try {
                String key = RedisKeys.getLogKey();
                // 每次插入10条
                int count = 10;
                for (int i = 0; i < count; i++) {
                    Object object = redisCache.rightPop(key);
                    if(object == null){
                        return;
                    }
                    OperateLogDTO log = (OperateLogDTO) object;
                    if (log == null) {
                        return;
                    }
                    SysLogOperateEntity entity = BeanUtil.copyProperties(log, SysLogOperateEntity.class);
                    sysLogOperateMapper.save(entity);
                }
            } catch (Exception e) {
                log.error("保存操作日志发生异常：{}", ExceptionUtils.getExceptionMessage(e));
            }
        }, 1, 10, TimeUnit.SECONDS);
    }
}