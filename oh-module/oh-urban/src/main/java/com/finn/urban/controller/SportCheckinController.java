package com.finn.urban.controller;

import com.finn.common.entity.Result;
import com.finn.urban.service.SportCheckinService;
import com.finn.urban.vo.SportCheckinVO;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 拍照打卡（微信小程序端）
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-19
 */
@RestController
@RequestMapping("/checkin")
public class SportCheckinController {

    private final SportCheckinService sportCheckinService;

    public SportCheckinController(SportCheckinService sportCheckinService){
        this.sportCheckinService = sportCheckinService;
    }
    /**
     * 保存运动打卡点（运动中途实时新增）
     * photos 字段为已上传到文件服务的 key 列表
     */
    @PostMapping("/handle")
    public Result<String> handle(@RequestBody SportCheckinVO vo) {
        return Result.ok(String.valueOf(sportCheckinService.saveCheckin(vo)));
    }

    /**
     * 根据ID查询打卡点列表
     */
    @GetMapping("/{id}")
    public Result<List<SportCheckinVO>> checkins(@PathVariable("id") Long id) {
        return Result.ok(sportCheckinService.listCheckinsByGroupId(id));
    }

    /**
     * 根据坐标查询半径 5 千米内最近的 80 条打卡记录（gcj02 坐标系，按时间倒序）
     */
    @GetMapping("/nearby")
    public Result<List<SportCheckinVO>> nearby(@RequestParam BigDecimal latitude,
                                               @RequestParam BigDecimal longitude) {
        return Result.ok(sportCheckinService.listNearby(latitude, longitude));
    }
}
