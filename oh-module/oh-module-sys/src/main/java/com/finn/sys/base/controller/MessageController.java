package com.finn.sys.base.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.sys.base.convert.MessageConvert;
import com.finn.sys.base.entity.MessageEntity;
import com.finn.sys.base.query.MessageQuery;
import com.finn.sys.base.service.MessageService;
import com.finn.sys.base.vo.MessageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name="系统消息")
public class MessageController {

    @Resource
    private MessageService messageService;

    @GetMapping("page")
    @Operation(summary = "分页")
    public Result<PageResult<MessageVO>> page(@ParameterObject @Valid MessageQuery query){
        PageResult<MessageVO> page = messageService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    public Result<MessageVO> get(@PathVariable("id") Long id){
        MessageEntity entity = messageService.getById(id);

        return Result.ok(MessageConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    public Result<String> save(@RequestBody MessageVO vo){
        messageService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    public Result<String> update(@RequestBody @Valid MessageVO vo){
        messageService.update(vo);
        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    public Result<String> delete(@RequestBody List<Long> idList){
        messageService.delete(idList);

        return Result.ok();
    }
}