package com.iris.sys.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iris.sys.base.vo.AnalysisVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iris.core.utils.Result;
import com.iris.sys.base.service.SysLogLoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 统计分析
 * 2023-11-26
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("sys/analysis")
@Tag(name = "统计分析")
public class AnalysisController {

    private final SysLogLoginService sysLogLoginService;

    public AnalysisController(SysLogLoginService sysLogLoginService){
        this.sysLogLoginService = sysLogLoginService;
    }

    /**
     * 统计最近几天相关操作情况
     * @param day 天数,跟 mysql.help_topic 的数量有关，最好保护超过1年
     * @param operation 操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误
     * @return 统计情况
     */
    @GetMapping("/latestLogin/{day}/{operation}")
    @Operation(summary = "统计最近几天相关操作情况")
    public Result<List<AnalysisVO>> latestLogin(@PathVariable("day")Integer day, @PathVariable("operation")Integer operation){
        return Result.ok(sysLogLoginService.latestDate(day, operation));
    }

    /**
     * 最近几天登录情况
     * 操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误
     * @param day
     * @return
     */
    @GetMapping("/latestLoginLog/{day}")
    @Operation(summary = "统计最近几天相关操作情况")
    public Result<Map<String, List<AnalysisVO>>> latestDateLogin(@PathVariable("day")Integer day){
        Map<String, List<AnalysisVO>> map = new HashMap<String, List<AnalysisVO>>();
        map.put("登录成功", sysLogLoginService.latestDate(day, 0));
        map.put("退出成功", sysLogLoginService.latestDate(day, 1));
        map.put("验证码错误", sysLogLoginService.latestDate(day, 2));
        map.put("账号密码错误", sysLogLoginService.latestDate(day, 3));
        return Result.ok(map);
    }
}
