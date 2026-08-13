package com.finn.urban.controller;

import com.finn.framework.aop.annotations.Log;
import com.finn.common.enums.OperateTypeEnum;
import com.finn.common.entity.PageResult;
import com.finn.common.entity.Result;
import com.finn.urban.convert.EventConvert;
import com.finn.urban.entity.Event;
import com.finn.urban.query.EventQuery;
import com.finn.urban.service.EventService;
import com.finn.urban.vo.EventVO;
import com.github.pagehelper.Page;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 事件表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-06-14 17:42:45
 * 
 */
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * 分页查询
     * @param query 查询条件
     * @return 列表
     */
    @GetMapping("/page")
    public Result<PageResult<EventVO>> page(@Valid EventQuery query) {
        Page<Event> page = eventService.page(query);
        return Result.ok(EventConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
    }

    /**
     * 分页查询
     * @param query 查询条件
     * @return 列表
     */
    @GetMapping("/myEvent")
    public Result<PageResult<EventVO>> myEvent(@Valid EventQuery query) {
        Page<Event> page = eventService.myEvent(query);
        return Result.ok(EventConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
    }

    /**
     * 获取案件详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<EventVO> info(@PathVariable("id") Long id) {
        return Result.ok(eventService.detail(id));
    }

    /**
     * 保存
     * @param vo 事件表信息
     * @return 提示信息
     */
    @PostMapping("/save")
    @Log(module = "事件表", name = "保存", type = OperateTypeEnum.INSERT)
    public Result<String> save(@RequestBody EventVO vo) {
        Long id = eventService.save(vo);
        return Result.ok(String.valueOf(id));
    }

    /**
     * 修改
     * @param vo 事件表信息
     * @return 提示信息
     */
    @PostMapping("/update")
    @Log(module = "事件表", name = "修改", type = OperateTypeEnum.UPDATE)
    public Result<String> update(@RequestBody EventVO vo) {
        eventService.update(vo);
        return Result.ok();
    }

    /**
     * 删除
     * @param idList 事件表ID集合
     * @return 提示信息
     */
    @PostMapping("/del")
    @Log(module = "事件表", name = "删除", type = OperateTypeEnum.DELETE)
    public Result<String> delete(@RequestBody List<Long> idList) {
        eventService.delete(idList);
        return Result.ok();
    }
}
