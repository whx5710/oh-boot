package com.iris.sys.base.service;

import com.iris.core.utils.PageResult;
import com.iris.sys.base.query.SysLogOperateQuery;
import com.iris.sys.base.vo.SysLogOperateVO;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SysLogOperateService {

    PageResult<SysLogOperateVO> page(SysLogOperateQuery query);
}