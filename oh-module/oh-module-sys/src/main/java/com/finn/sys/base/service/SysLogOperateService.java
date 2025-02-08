package com.finn.sys.base.service;

import com.finn.core.utils.PageResult;
import com.finn.sys.base.query.SysLogOperateQuery;
import com.finn.sys.base.vo.SysLogOperateVO;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SysLogOperateService {

    PageResult<SysLogOperateVO> page(SysLogOperateQuery query);
}