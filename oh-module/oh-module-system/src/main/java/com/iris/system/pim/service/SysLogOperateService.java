package com.iris.system.pim.service;

import com.iris.system.pim.entity.SysLogOperateEntity;
import com.iris.system.pim.query.SysLogOperateQuery;
import com.iris.system.pim.vo.SysLogOperateVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.BaseService;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SysLogOperateService extends BaseService<SysLogOperateEntity> {

    PageResult<SysLogOperateVO> page(SysLogOperateQuery query);
}