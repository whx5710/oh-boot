package com.iris.system.base.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.BaseService;
import com.iris.system.base.query.SmsLogQuery;
import com.iris.system.base.vo.SmsLogVO;
import com.iris.system.base.entity.SmsLogEntity;

/**
 * 短信日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SmsLogService extends BaseService<SmsLogEntity> {

    PageResult<SmsLogVO> page(SmsLogQuery query);

}