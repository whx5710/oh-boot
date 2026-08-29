package com.finn.urban.controller;

import com.finn.common.entity.HashDto;
import com.finn.common.entity.PageResult;
import com.finn.common.entity.Result;
import com.finn.common.enums.OperateTypeEnum;
import com.finn.framework.aop.annotations.Log;
import com.finn.framework.exception.ServerException;
import com.finn.framework.security.user.SecurityUser;
import com.finn.urban.convert.SportRecordConvert;
import com.finn.urban.entity.SportRecordEntity;
import com.finn.urban.query.SportRecordQuery;
import com.finn.urban.service.SportCheckinService;
import com.finn.urban.service.SportRecordService;
import com.finn.urban.vo.SportRecordVO;
import com.github.pagehelper.Page;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 运动记录表（微信小程序端）
 * 提供运动开始/结束、拍照打卡保存、历史分页、详情(含轨迹与打卡点)接口
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-19
 */
@RestController
@RequestMapping("/sport")
public class SportRecordController {

    private final SportRecordService sportRecordService;

    private final SportCheckinService sportCheckinService;

    public SportRecordController(SportRecordService sportRecordService, SportCheckinService sportCheckinService) {
        this.sportRecordService = sportRecordService;
        this.sportCheckinService = sportCheckinService;
    }

    /**
     * 分页查询（列表项含打卡数量，不含坐标点）
     */
    @GetMapping("/page")
    // @PreAuthorize("hasAuthority('urban:sport-record:page')")
    public Result<PageResult<SportRecordVO>> page(@Valid SportRecordQuery query) {
        Page<SportRecordEntity> page = sportRecordService.page(query);
        List<SportRecordVO> vos = SportRecordConvert.INSTANCE.convertList(page.getResult());
        sportCheckinService.fillCheckinCount(vos);
        return Result.ok(vos, page.getTotal());
    }

    /**
     * 我的分页查询（列表项含打卡数量，不含坐标点）
     */
    @GetMapping("/mine")
    public Result<PageResult<SportRecordVO>> mine(@Valid SportRecordQuery query) {
        Long userId = SecurityUser.getUserId();
        if(userId == null){
            throw new ServerException("请先登录");
        }
        query.setUserId(userId);
        Page<SportRecordEntity> page = sportRecordService.page(query);
        List<SportRecordVO> vos = SportRecordConvert.INSTANCE.convertList(page.getResult());
        sportCheckinService.fillCheckinCount(vos);
        return Result.ok(vos, page.getTotal());
    }

    /**
     * 广场
     */
    @GetMapping("/piazza")
    public Result<PageResult<SportRecordVO>> piazza(@Valid SportRecordQuery query) {
        query.setVisibility(1);
        query.setOrderBy("release_time desc");
        Page<SportRecordEntity> page = sportRecordService.page(query);
        List<SportRecordVO> vos = SportRecordConvert.INSTANCE.convertList(page.getResult());
//        sportCheckinService.fillCheckinCount(vos);
        return Result.ok(vos, page.getTotal());
    }




    /**
     * 根据ID查询详情（含轨迹坐标点 points，不含打卡点 checkins）
     */
    @GetMapping("/{id}")
    // @PreAuthorize("hasAuthority('urban:sport-record:info')")
    public Result<SportRecordVO> info(@PathVariable("id") Long id) {
        return Result.ok(sportRecordService.detailBaseWithTrack(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('urban:sport-record:save')")
    public Result<String> save(@RequestBody SportRecordVO vo) {
        Long id = sportRecordService.save(vo);
        return Result.ok(String.valueOf(id));
    }

    /**
     * 开始运动：创建运动记录，返回运动记录ID
     */
    @PostMapping("/start")
    @Log(module = "运动", name = "开始", type = OperateTypeEnum.INSERT)
    // @PreAuthorize("hasAuthority('urban:sport-record:start')")
    public Result<String> start(@RequestBody SportRecordVO vo) {
        Long id = sportRecordService.start(vo);
        return Result.ok(String.valueOf(id));
    }

    /**
     * 结束运动：更新记录状态并按 clientId 去重落库打卡点
     */
    @PostMapping("/finish/{id}")
    @Log(module = "运动", name = "结束", type = OperateTypeEnum.UPDATE)
    // @PreAuthorize("hasAuthority('urban:sport-record:finish')")
    public Result<String> finish(@PathVariable("id") Long id, @RequestBody SportRecordVO vo) {
        return Result.ok(sportRecordService.finish(id, vo));
    }

    /**
     * 是否正在运动期间
     */
    @GetMapping("/isRunning/{id}")
    // @PreAuthorize("hasAuthority('urban:sport-record:isRun')")
    public Result<Boolean> isRunning(@PathVariable("id") Long id) {
        return Result.ok(sportRecordService.isRunning(id));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('urban:sport-record:update')")
    public Result<String> update(@RequestBody SportRecordVO vo) {
        sportRecordService.update(vo);
        return Result.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/del")
    @PreAuthorize("hasAuthority('urban:sport-record:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sportRecordService.delete(idList);
        return Result.ok();
    }

    /**
     * 按时间统计运动情况
     * @param query
     * @return
     */
    @PostMapping("/statistics")
    public Result<HashDto> statistics(@RequestBody SportRecordQuery query){
        return Result.ok(sportRecordService.statistics(query));
    }

    /**
     * 公开、取消公开
     * id 运动ID
     * visibility 0非公开 1公开
     * coverFileId 封面图片
     */
    @PostMapping("/visibility")
    public Result<String> handlerVisibility(@RequestBody SportRecordVO vo){
        return Result.ok(sportRecordService.handlerVisibility(vo));
    }

}
