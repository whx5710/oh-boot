package com.iris.system.base.service;

import com.iris.system.base.query.SysLogOperateQuery;
import com.iris.system.base.vo.SysLogOperateVO;
import com.iris.framework.common.utils.PageResult;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SysLogOperateService {

    PageResult<SysLogOperateVO> page(SysLogOperateQuery query);
}