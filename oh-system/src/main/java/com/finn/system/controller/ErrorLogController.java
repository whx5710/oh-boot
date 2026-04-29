package com.finn.system.controller;

import com.finn.system.service.ErrorLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统错误日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-04-29 18:38:22
 * 
 */
@RestController
@RequestMapping("sys/error_log")
public class ErrorLogController {
    private final ErrorLogService errorLogService;

    public ErrorLogController(ErrorLogService errorLogService) {
        this.errorLogService = errorLogService;
    }

}
