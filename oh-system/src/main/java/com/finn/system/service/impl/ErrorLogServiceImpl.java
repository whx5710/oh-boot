package com.finn.system.service.impl;

import com.finn.system.mapper.ErrorLogMapper;
import com.finn.system.service.ErrorLogService;
import org.springframework.stereotype.Service;

/**
 * 系统错误日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-04-29 18:38:22
 *
 */
@Service
public class ErrorLogServiceImpl implements ErrorLogService {

    private final ErrorLogMapper errorLogMapper;
    public ErrorLogServiceImpl(ErrorLogMapper errorLogMapper) {
        this.errorLogMapper = errorLogMapper;
    }

}
