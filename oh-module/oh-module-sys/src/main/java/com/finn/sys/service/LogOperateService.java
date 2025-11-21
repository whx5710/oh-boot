package com.finn.sys.service;

import com.finn.core.utils.PageResult;
import com.finn.sys.query.LogOperateQuery;
import com.finn.sys.vo.LogOperateVO;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface LogOperateService {

    PageResult<LogOperateVO> page(LogOperateQuery query);

    /**
     * 导出操作日志
     * @param query 查询条件
     */
    void export(LogOperateQuery query);
}