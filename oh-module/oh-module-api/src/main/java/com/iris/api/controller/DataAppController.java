package com.iris.api.controller;

import com.iris.api.convert.DataAppConvert;
import com.iris.api.entity.DataAppEntity;
import com.iris.api.query.DataAppQuery;
import com.iris.api.query.DataMsgQuery;
import com.iris.api.service.DataAppService;
import com.iris.api.service.DataMsgService;
import com.iris.api.utils.RunnerHandler;
import com.iris.api.vo.DataAppVO;
import com.iris.api.vo.DataMsgVO;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.utils.AssertUtils;
import com.iris.framework.common.utils.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import com.iris.framework.common.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 客户端
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-29
*/
@RestController
@RequestMapping("/external/app")
@Tag(name="客户端")
public class DataAppController {

    @Resource
    DataAppService dataAppService;

    @Resource
    RunnerHandler runnerHandler;

    @Resource
    DataMsgService dataMsgService;

    @GetMapping("/page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('external:app:page')")
    public Result<PageResult<DataAppVO>> page(@ParameterObject @Valid DataAppQuery query){
        PageResult<DataAppVO> page = dataAppService.page(query);

        return Result.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('external:app:info')")
    public Result<DataAppVO> get(@PathVariable("id") Long id){
        DataAppEntity entity = dataAppService.getById(id);
        return Result.ok(DataAppConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('external:app:save')")
    public Result<String> save(@RequestBody DataAppVO vo){
        dataAppService.save(vo);
        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('external:app:update')")
    public Result<String> update(@RequestBody @Valid DataAppVO vo){
        dataAppService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('external:app:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        dataAppService.delete(idList);

        return Result.ok();
    }

    // 刷新权限
    @GetMapping("/appAuthority")
    public Result<String> appAuthority(){
        runnerHandler.appAuthority();
        return Result.ok();
    }

    @GetMapping("/logPage")
    @Operation(summary = "接口日志分页")
    @PreAuthorize("hasAuthority('external:app:page')")
    public Result<PageResult<DataMsgVO>> logPage(@ParameterObject @Valid DataMsgQuery query){
        PageResult<DataMsgVO> page = dataMsgService.page(query);
        return Result.ok(page);
    }

    @GetMapping("/logErrPage")
    @Operation(summary = "根据客户端ID查询接口日志（无鉴权）")
    public Result<PageResult<DataMsgVO>> logErrPage(@ParameterObject @Valid DataMsgQuery query, HttpServletRequest request){
        String clientId = query.getClientId();
        AssertUtils.isBlank(clientId,"客户端ID");
        String secretKey = request.getHeader("OH-SECRET-KEY");
        AssertUtils.isBlank(secretKey,"客户端密钥");
        DataAppVO dataAppVO = dataAppService.findByClientId(clientId);
        if(dataAppVO == null || dataAppVO.getSecretKey() == null || !dataAppVO.getSecretKey().equals(secretKey)){
            throw new ServerException("客户端和密钥不匹配，请检查。");
        }
        query.setState("3"); // 状态0未处理1处理2未找到对应的服务类3业务处理失败
        PageResult<DataMsgVO> page = dataMsgService.page(query);
        return Result.ok(page);
    }

    @DeleteMapping("/deleteLog")
    @Operation(summary = "删除请求记录")
    @PreAuthorize("hasAuthority('external:app:delete')")
    public Result<String> deleteLog(@RequestBody List<Long> idList){
        dataMsgService.delete(idList);

        return Result.ok();
    }

    @GetMapping("/deleteByDate/{date}")
    @Operation(summary = "根据时间删除报文记录")
    @PreAuthorize("hasAuthority('external:app:delete')")
    public Result<String> deleteByDate(@PathVariable("date")String date){
        dataMsgService.deleteByDate(date);
        return Result.ok();
    }
}