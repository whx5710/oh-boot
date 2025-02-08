package com.finn.sys.base.service.impl;

import com.finn.core.utils.PageResult;
import com.finn.sys.base.convert.SmsLogConvert;
import com.finn.sys.base.entity.SmsLogEntity;
import com.finn.sys.base.mapper.SmsLogMapper;
import com.finn.sys.base.query.SmsLogQuery;
import com.finn.sys.base.service.SmsLogService;
import com.finn.sys.base.vo.SmsLogVO;
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
        List<SmsLogEntity> list = smsLogMapper.getList(query);
        return new PageResult<>(SmsLogConvert.INSTANCE.convertList(list), query.getTotal());
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