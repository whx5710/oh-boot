package com.finn.system.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.system.convert.MessageConvert;
import com.finn.system.entity.MessageEntity;
import com.finn.system.query.MessageQuery;
import com.finn.system.service.MessageService;
import com.finn.system.vo.MessageVO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 系统消息
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-10-10
*/
@RestController
@RequestMapping("message")
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 分页
     * @param query 查询条件
     * @return 分页列表
     */
    @GetMapping("page")
    public Result<PageResult<MessageVO>> page(@Valid MessageQuery query){
        PageResult<MessageVO> page = messageService.page(query);

        return Result.ok(page);
    }

    /**
     * 根据ID获取消息
     * @param id 消息ID
     * @return 消息数据
     */
    @GetMapping("{id}")
    public Result<MessageVO> get(@PathVariable("id") Long id){
        MessageEntity entity = messageService.getById(id);

        return Result.ok(MessageConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存消息
     * @param vo 消息
     * @return 提示信息
     */
    @PostMapping
    public Result<String> save(@RequestBody MessageVO vo){
        messageService.save(vo);

        return Result.ok();
    }

    /**
     * 修改
     * @param vo 消息
     * @return 提示信息
     */
    @PostMapping("/update")
    public Result<String> update(@RequestBody @Valid MessageVO vo){
        messageService.update(vo);
        return Result.ok();
    }

    /**
     * 删除
     * @param idList 消息ID集合
     * @return 提示信息
     */
    @PostMapping("/del")
    public Result<String> delete(@RequestBody List<Long> idList){
        messageService.delete(idList);

        return Result.ok();
    }
}