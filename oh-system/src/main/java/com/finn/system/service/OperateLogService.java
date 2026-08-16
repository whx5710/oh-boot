package com.finn.system.service;

import com.finn.common.entity.PageResult;
import com.finn.system.query.OperateLogQuery;
import com.finn.system.vo.OperateLogVO;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface OperateLogService {

    PageResult<OperateLogVO> page(OperateLogQuery query);

    /**
     * 导出操作日志
     * @param query 查询条件
     */
    void export(OperateLogQuery query);
}