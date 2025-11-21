package com.finn.sys.service;

import com.finn.core.utils.PageResult;
import com.finn.sys.entity.SmsLogEntity;
import com.finn.sys.query.SmsLogQuery;
import com.finn.sys.vo.SmsLogVO;

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