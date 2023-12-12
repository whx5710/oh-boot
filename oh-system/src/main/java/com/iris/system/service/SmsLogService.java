package com.iris.system.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;
import com.iris.system.entity.SmsLogEntity;
import com.iris.system.query.SmsLogQuery;
import com.iris.system.vo.SmsLogVO;

/**
 * 短信日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SmsLogService extends BaseService<SmsLogEntity> {

    PageResult<SmsLogVO> page(SmsLogQuery query);

}