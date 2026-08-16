package com.finn.system.service;

import com.finn.common.entity.PageResult;
import com.finn.system.query.ErrorLogQuery;
import com.finn.system.vo.ErrorLogVO;

import java.util.List;

/**
 * 系统错误日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-04-29 18:38:22
 *
 */
public interface ErrorLogService {
    PageResult<ErrorLogVO> page(ErrorLogQuery query);

    void export(ErrorLogQuery query);

    long delete(List<Long> idList);

    long deleteByDate(String date);
}
