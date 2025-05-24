package com.finn.sys.base.controller;

import com.finn.core.utils.Result;
import com.finn.support.mapper.LogLoginMapper;
import com.finn.support.service.LogLoginService;
import com.finn.support.vo.AnalysisVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计分析
 * @since 2023-11-26
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("sys/analysis")
public class AnalysisController {

    private final LogLoginService logLoginService;

    public AnalysisController(LogLoginService logLoginService, LogLoginMapper logLoginMapper){
        this.logLoginService = logLoginService;
    }

    /**
     * 统计最近几天相关操作情况
     * @param day 天数,跟 mysql.help_topic 的数量有关，最好保护超过1年
     * @param operation 操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误
     * @return 统计情况
     */
    @GetMapping("/latestLogin/{day}/{operation}")
    public Result<List<AnalysisVO>> latestLogin(@PathVariable("day")Integer day, @PathVariable("operation")Integer operation){
        return Result.ok(logLoginService.latestDateCount(day, operation));
    }

    /**
     * 最近几天登录情况
     * @param day day
     * @return
     */
    @GetMapping("/latestLoginLog/{day}")
    public Result<Map<String, List<AnalysisVO>>> latestDateLogin(@PathVariable("day")Integer day){
        Map<String, List<AnalysisVO>> map = new HashMap<String, List<AnalysisVO>>();
        map.put("登录成功", logLoginService.latestDateCount(day, 0));
        map.put("退出成功", logLoginService.latestDateCount(day, 1));
        map.put("验证码错误", logLoginService.latestDateCount(day, 2));
        map.put("账号密码错误", logLoginService.latestDateCount(day, 3));
        return Result.ok(map);
    }
}
