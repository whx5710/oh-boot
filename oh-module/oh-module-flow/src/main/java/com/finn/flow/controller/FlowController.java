package com.finn.flow.controller;

import com.finn.core.utils.DateUtils;
import com.finn.core.utils.Result;
import com.finn.flow.convert.FlowConvert;
import com.finn.flow.entity.FlowEntity;
import com.finn.flow.query.FlowQuery;
import com.finn.flow.service.FlowService;
import com.finn.flow.service.ProcessHandlerService;
import com.finn.flow.vo.FlowVO;
import com.finn.flow.vo.ProcessVO;
import com.finn.core.utils.PageResult;
import jakarta.validation.Valid;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* 自定义流程表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-12-19
*/
@RestController
@RequestMapping("/flow")
public class FlowController {
    private final FlowService flowService;

    private final ProcessHandlerService processHandlerService;

    public FlowController(FlowService flowService, ProcessHandlerService processHandlerService){
        this.flowService = flowService;
        this.processHandlerService = processHandlerService;
    }

    /**
     * 分页
     * @param query
     * @return
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('flow:page')")
    public Result<PageResult<FlowVO>> page(@Valid FlowQuery query){
        PageResult<FlowVO> page = flowService.page(query);
        return Result.ok(page);
    }

    /**
     * 根据ID获取流程信息
     * @param id 流程ID
     * @return 流程信息
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('finn:flow:info')")
    public Result<FlowVO> get(@PathVariable("id") Long id){
        FlowEntity entity = flowService.getById(id);

        return Result.ok(FlowConvert.INSTANCE.convert(entity));
    }

    /**
     * 根据keyCode保存或更新
     * @param vo 流程信息
     * @return 提示信息
     */
    @PostMapping("/saveOrUpdate")
    @PreAuthorize("hasAuthority('flow:saveOrUpdate')")
    public Result<String> saveOrUpdate(@RequestBody FlowVO vo){
        flowService.save(vo);

        return Result.ok();
    }

    /**
     * 修改
     * @param vo 流程信息
     * @return 提示信息
     */
    @PutMapping
    @PreAuthorize("hasAuthority('finn:flow:update')")
    public Result<String> update(@RequestBody @Valid FlowVO vo){
        flowService.update(vo);

        return Result.ok();
    }

    /**
     * 删除
     * @param idList 流程ID集合
     * @return 提示信息
     */
    @DeleteMapping
    @PreAuthorize("hasAuthority('flow:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        flowService.delete(idList);

        return Result.ok();
    }

    @GetMapping("/listProcessByKey/{processCode}")
    public Result<List<ProcessVO>> listProcessByKey(@PathVariable("processCode") String processCode){
        List<ProcessVO> processVOList = new ArrayList<>();
        List<ProcessDefinition> list = processHandlerService.listProcessByKey(processCode);
        for(ProcessDefinition processDefinition: list){
            ProcessVO processVO = new ProcessVO();
            processVO.setId(processDefinition.getId());
            String id = processDefinition.getDeploymentId();
            Deployment deployment = processHandlerService.getDeployment(id);
            processVO.setDeploymentId(id);
            Date date = deployment.getDeploymentTime();
            processVO.setCreateTime(DateUtils.dateToLocalDate(date));
            processVO.setProcessKey(processDefinition.getKey());
            processVO.setVersion(processDefinition.getVersion());
            processVO.setVersionTag(processDefinition.getVersionTag());
            processVO.setName(processDefinition.getName());
            processVOList.add(processVO);
        }
        return Result.ok(processVOList);
    }
}