package com.finn.system.service;

import com.finn.core.utils.PageResult;
import com.finn.system.entity.SmsLogEntity;
import com.finn.system.query.SmsLogQuery;
import com.finn.system.vo.SmsLogVO;

/**
 * 短信日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SmsLogService {

    PageResult<SmsLogVO> page(SmsLogQuery query);

    SmsLogEntity getById(Long id);

    int save(SmsLogEntity entity);

}