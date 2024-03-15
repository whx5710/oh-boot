package com.iris.system.quartz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.system.quartz.convert.ScheduleJobLogConvert;
import com.iris.system.quartz.dao.ScheduleJobLogDao;
import com.iris.system.quartz.entity.ScheduleJobLogEntity;
import com.iris.system.quartz.query.ScheduleJobLogQuery;
import com.iris.system.quartz.service.ScheduleJobLogService;
import com.iris.system.quartz.vo.ScheduleJobLogVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 定时任务日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class ScheduleJobLogServiceImpl extends BaseServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {
    @Override
    public PageResult<ScheduleJobLogVO> page(ScheduleJobLogQuery query) {
        IPage<ScheduleJobLogEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(ScheduleJobLogConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    private LambdaQueryWrapper<ScheduleJobLogEntity> getWrapper(ScheduleJobLogQuery query){
        LambdaQueryWrapper<ScheduleJobLogEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(query.getJobName()), ScheduleJobLogEntity::getJobName, query.getJobName());
        wrapper.like(StrUtil.isNotBlank(query.getJobGroup()), ScheduleJobLogEntity::getJobGroup, query.getJobGroup());
        wrapper.eq(query.getJobId() != null, ScheduleJobLogEntity::getJobId, query.getJobId());
        wrapper.orderByDesc(ScheduleJobLogEntity::getId);
        return wrapper;
    }

}