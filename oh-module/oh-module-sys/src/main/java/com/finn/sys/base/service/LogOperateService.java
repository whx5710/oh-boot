package com.finn.sys.base.service;

import com.finn.core.utils.PageResult;
import com.finn.sys.base.query.LogOperateQuery;
import com.finn.sys.base.vo.LogOperateVO;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface LogOperateService {

    PageResult<LogOperateVO> page(LogOperateQuery query);
}