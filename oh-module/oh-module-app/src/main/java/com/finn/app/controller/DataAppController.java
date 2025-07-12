package com.finn.app.controller;

import com.finn.app.service.DataFunctionAuthorityService;
import com.finn.core.constant.Constant;
import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.framework.entity.api.DataAppDTO;
import com.finn.core.exception.ServerException;
import com.finn.app.convert.DataAppConvert;
import com.finn.app.entity.DataAppEntity;
import com.finn.app.query.DataAppQuery;
import com.finn.app.query.DataMsgQuery;
import com.finn.app.service.DataAppService;
import com.finn.app.service.DataMsgService;
import com.finn.app.vo.DataMsgVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 客户端
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-29
*/
@RestController
@RequestMapping("/sys/app")
public class DataAppController {

    @Resource
    DataAppService dataAppService;

    @Resource
    DataFunctionAuthorityService dataFunctionAuthorityService;

    @Resource
    DataMsgService dataMsgService;

    /**
     * 分页
     * @param query
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:app:page')")
    public Result<PageResult<DataAppDTO>> page(@Valid DataAppQuery query){
        PageResult<DataAppDTO> page = dataAppService.page(query);

        return Result.ok(page);
    }

    /**
     * 根据ID获取客户端信息
     * @param id 客户端ID
     * @return app数据
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:app:info')")
    public Result<DataAppDTO> get(@PathVariable("id") Long id){
        DataAppEntity entity = dataAppService.getById(id);
        return Result.ok(DataAppConvert.INSTANCE.convert(entity));
    }

    /**
     * 保存
     * @param vo
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:app:save')")
    public Result<String> save(@RequestBody DataAppDTO vo){
        dataAppService.save(vo);
        return Result.ok();
    }

    /**
     * 修改
     * @param vo
     * @return
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:app:update')")
    public Result<String> update(@RequestBody @Valid DataAppDTO vo){
        dataAppService.update(vo);

        return Result.ok();
    }

    /**
     * 删除
     * @param idList
     * @return
     */
    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:app:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        dataAppService.delete(idList);

        return Result.ok();
    }

    // 刷新权限
    @GetMapping("/appAuthority")
    public Result<String> appAuthority(){
        dataFunctionAuthorityService.refreshAppAuthority(null);
        return Result.ok();
    }

    /**
     * 接口日志分页
     * @param query
     * @return
     */
    @GetMapping("/logPage")
    @PreAuthorize("hasAuthority('sys:app:page')")
    public Result<PageResult<DataMsgVO>> logPage(@Valid DataMsgQuery query){
        PageResult<DataMsgVO> page = dataMsgService.page(query);
        return Result.ok(page);
    }

    /**
     * 根据客户端ID查询接口日志（无鉴权）
     * @param query p
     * @param request r
     * @return r
     */
    @GetMapping("/errLogPage")
    public Result<PageResult<DataMsgVO>> errLogPage(@Valid DataMsgQuery query, HttpServletRequest request){
        String clientId = query.getClientId();
        AssertUtils.isBlank(clientId,"客户端ID");
        String secretKey = query.getSecretKey();
        if(ObjectUtils.isEmpty(secretKey)){
            secretKey = request.getHeader(Constant.SECRET_KEY);
        }
        AssertUtils.isBlank(secretKey,"客户端密钥");
        DataAppDTO dataAppDTO = dataAppService.findByClientId(clientId);
        if(dataAppDTO == null || dataAppDTO.getSecretKey() == null || !dataAppDTO.getSecretKey().equals(secretKey)){
            throw new ServerException("客户端和密钥不匹配，请检查。");
        }
        query.setState("3"); // 状态0未处理1处理2未找到对应的服务类3业务处理失败
        PageResult<DataMsgVO> page = dataMsgService.page(query);
        return Result.ok(page);
    }

    /**
     * 删除请求记录
     * @param idList
     * @return
     */
    @DeleteMapping("/deleteLog")
    @PreAuthorize("hasAuthority('sys:app:delete')")
    public Result<String> deleteLog(@RequestBody List<Long> idList){
        dataMsgService.delete(idList);

        return Result.ok();
    }

    /**
     * 根据时间删除报文记录
     * @param date
     * @return
     */
    @GetMapping("/deleteByDate/{date}")
    @PreAuthorize("hasAuthority('sys:app:delete')")
    public Result<String> deleteByDate(@PathVariable("date")String date){
        dataMsgService.deleteByDate(date);
        return Result.ok();
    }
}