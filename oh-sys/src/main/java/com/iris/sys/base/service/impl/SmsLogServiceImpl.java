package com.iris.sys.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.framework.common.utils.PageResult;
import com.iris.sys.base.mapper.SmsLogMapper;
import com.iris.sys.base.query.SmsLogQuery;
import com.iris.sys.base.vo.SmsLogVO;
import com.iris.sys.base.convert.SmsLogConvert;
import com.iris.sys.base.entity.SmsLogEntity;
import com.iris.sys.base.service.SmsLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短信日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SmsLogServiceImpl implements SmsLogService {

    private final SmsLogMapper smsLogMapper;

    public SmsLogServiceImpl(SmsLogMapper smsLogMapper) {
        this.smsLogMapper = smsLogMapper;
    }

    @Override
    public PageResult<SmsLogVO> page(SmsLogQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SmsLogEntity> list = smsLogMapper.getList(query);
        PageInfo<SmsLogEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SmsLogConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public SmsLogEntity getById(Long id) {
        return smsLogMapper.getById(id);
    }

    @Override
    public int save(SmsLogEntity entity) {
        return smsLogMapper.insertSmsLog(entity);
    }

}