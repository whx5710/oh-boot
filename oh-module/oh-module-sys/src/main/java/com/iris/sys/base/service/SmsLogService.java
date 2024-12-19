package com.iris.sys.base.service;

import com.iris.core.utils.PageResult;
import com.iris.sys.base.entity.SmsLogEntity;
import com.iris.sys.base.query.SmsLogQuery;
import com.iris.sys.base.vo.SmsLogVO;

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