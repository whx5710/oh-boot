package com.finn.sys.base.service;

import com.finn.core.utils.PageResult;
import com.finn.sys.base.entity.SmsLogEntity;
import com.finn.sys.base.query.SmsLogQuery;
import com.finn.sys.base.vo.SmsLogVO;

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