package com.iris.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.system.vo.SysLogOperateVO;
import jakarta.annotation.PostConstruct;
import com.iris.framework.common.cache.RedisCache;
import com.iris.framework.common.cache.RedisKeys;
import com.iris.framework.common.utils.ExceptionUtils;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.framework.operatelog.dto.OperateLogDTO;
import com.iris.system.convert.SysLogOperateConvert;
import com.iris.system.dao.SysLogOperateDao;
import com.iris.system.entity.SysLogOperateEntity;
import com.iris.system.query.SysLogOperateQuery;
import com.iris.system.service.SysLogOperateService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SysLogOperateServiceImpl extends BaseServiceImpl<SysLogOperateDao, SysLogOperateEntity> implements SysLogOperateService {
    private final RedisCache redisCache;

    public SysLogOperateServiceImpl(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    public PageResult<SysLogOperateVO> page(SysLogOperateQuery query) {
        IPage<SysLogOperateEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(SysLogOperateConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<SysLogOperateEntity> getWrapper(SysLogOperateQuery query) {
        LambdaQueryWrapper<SysLogOperateEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(query.getStatus() != null, SysLogOperateEntity::getStatus, query.getStatus());
        wrapper.like(StrUtil.isNotBlank(query.getRealName()), SysLogOperateEntity::getRealName, query.getRealName());
        wrapper.like(StrUtil.isNotBlank(query.getModule()), SysLogOperateEntity::getModule, query.getModule());
        wrapper.like(StrUtil.isNotBlank(query.getReqUri()), SysLogOperateEntity::getReqUri, query.getReqUri());
        wrapper.orderByDesc(SysLogOperateEntity::getId);
        return wrapper;
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
                    OperateLogDTO log = (OperateLogDTO) redisCache.rightPop(key);
                    if (log == null) {
                        return;
                    }

                    SysLogOperateEntity entity = BeanUtil.copyProperties(log, SysLogOperateEntity.class);
                    baseMapper.insert(entity);
                }
            } catch (Exception e) {
                log.error("SysLogOperateServiceImpl.saveLog Error：" + ExceptionUtils.getExceptionMessage(e));
            }
        }, 1, 10, TimeUnit.SECONDS);
    }
}