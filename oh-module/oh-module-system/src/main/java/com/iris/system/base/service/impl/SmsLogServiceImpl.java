package com.iris.system.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.impl.BaseServiceImpl;
import com.iris.system.base.dao.SmsLogDao;
import com.iris.system.base.query.SmsLogQuery;
import com.iris.system.base.vo.SmsLogVO;
import com.iris.system.base.convert.SmsLogConvert;
import com.iris.system.base.entity.SmsLogEntity;
import com.iris.system.base.service.SmsLogService;
import org.springframework.stereotype.Service;

/**
 * 短信日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SmsLogServiceImpl extends BaseServiceImpl<SmsLogDao, SmsLogEntity> implements SmsLogService {

    public SmsLogServiceImpl() {
    }

    @Override
    public PageResult<SmsLogVO> page(SmsLogQuery query) {
        IPage<SmsLogEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(SmsLogConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<SmsLogEntity> getWrapper(SmsLogQuery query){
        LambdaQueryWrapper<SmsLogEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(query.getPlatform() != null, SmsLogEntity::getPlatform, query.getPlatform());
        wrapper.like(query.getPlatformId() != null, SmsLogEntity::getPlatformId, query.getPlatformId());
        wrapper.orderByDesc(SmsLogEntity::getId);
        return wrapper;
    }

}