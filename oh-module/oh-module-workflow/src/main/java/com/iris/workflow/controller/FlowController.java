package com.iris.workflow.controller;

import com.iris.framework.common.exception.ServerException;
import com.iris.workflow.convert.FlowConvert;
import com.iris.workflow.entity.FlowEntity;
import com.iris.workflow.query.FlowQuery;
import com.iris.workflow.service.FlowService;
import com.iris.workflow.service.ProcessHandlerService;
import com.iris.workflow.utils.BpmnUtils;
import com.iris.workflow.vo.FlowVO;
import com.iris.workflow.vo.ProcessVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.common.utils.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.batik.transcoder.TranscoderException;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* 自定义流程表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-12-19
*/
@RestController
@RequestMapping("/flow")
@Tag(name="自定义流程表")
public class FlowController {
    private final FlowService flowService;

    private final ProcessHandlerService processHandlerService;

    public FlowController(FlowService flowService, ProcessHandlerService processHandlerService){
        this.flowService = flowService;
        this.processHandlerService = processHandlerService;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('flow:page')")
    public Result<PageResult<FlowVO>> page(@ParameterObject @Valid FlowQuery query){
        PageResult<FlowVO> page = flowService.page(query);
        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('iris:flow:info')")
    public Result<FlowVO> get(@PathVariable("id") Long id){
        FlowEntity entity = flowService.getById(id);

        return Result.ok(FlowConvert.INSTANCE.convert(entity));
    }

    @PostMapping("/saveOrUpdate")
    @Operation(summary = "根据keyCode保存或更新")
    @PreAuthorize("hasAuthority('flow:saveOrUpdate')")
    public Result<String> saveOrUpdate(@RequestBody FlowVO vo){
        flowService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('iris:flow:update')")
    public Result<String> update(@RequestBody @Valid FlowVO vo){
        flowService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('flow:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        flowService.delete(idList);

        return Result.ok();
    }

    /**
     * 根据流程code，将svg图片转成png图片
     * @param keyCode
     * @param response
     */
    @GetMapping("/svg2png/{keyCode}")
    public void svg2png(@PathVariable("keyCode") String keyCode, HttpServletResponse response){
        FlowEntity flowEntity = flowService.getByKey(keyCode);
        try {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            BpmnUtils.convertToPng(flowEntity.getSvgStr(), response.getOutputStream());
        } catch (TranscoderException | IOException e) {
            throw new ServerException(e.getMessage());
        }
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
            processVO.setCreateTime(deployment.getDeploymentTime());
            processVO.setProcessKey(processDefinition.getKey());
            processVO.setVersion(processDefinition.getVersion());
            processVO.setVersionTag(processDefinition.getVersionTag());
            processVO.setName(processDefinition.getName());
            processVOList.add(processVO);
        }
        return Result.ok(processVOList);
    }
}