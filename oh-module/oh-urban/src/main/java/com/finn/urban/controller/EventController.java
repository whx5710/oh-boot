package com.finn.urban.controller;

import com.finn.framework.aop.annotations.Log;
import com.finn.framework.common.enums.OperateTypeEnum;
import com.finn.framework.entity.PageResult;
import com.finn.framework.entity.Result;
import com.finn.urban.query.EventQuery;
import com.finn.urban.service.EventService;
import com.finn.urban.vo.EventVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @GetMapping("page")
    @PreAuthorize("hasAuthority('event:page')")
    public Result<PageResult<EventVO>> page(@Valid EventQuery query) {
        PageResult<EventVO> page = eventService.page(query);

        return Result.ok(page);
    }

    /**
     * 保存
     * @param vo 事件表信息
     * @return 提示信息
     */
    @PostMapping
    @Log(module = "事件表", name = "保存", type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('event:save')")
    public Result<String> save(@RequestBody EventVO vo) {
        Long id = eventService.save(vo);
        return Result.ok(String.valueOf(id));
    }

    /**
     * 修改
     * @param vo 事件表信息
     * @return 提示信息
     */
    @PutMapping
    @Log(module = "事件表", name = "修改", type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('event:update')")
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
    @PreAuthorize("hasAuthority('event:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        eventService.delete(idList);

        return Result.ok();
    }
}
