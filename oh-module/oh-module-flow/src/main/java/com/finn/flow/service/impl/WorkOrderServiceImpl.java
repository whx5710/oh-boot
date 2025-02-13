package com.finn.flow.service.impl;

import com.finn.core.entity.HashDto;
import com.github.pagehelper.Page;
import com.finn.core.cache.RedisCache;
import com.finn.core.utils.Tools;
import com.finn.core.utils.JsonUtils;
import com.finn.flow.convert.WorkOrderConvert;
import com.finn.flow.entity.WorkOrderEntity;
import com.finn.flow.mapper.WorkOrderMapper;
import com.finn.flow.query.WorkOrderQuery;
import com.finn.flow.service.ProcessHandlerService;
import com.finn.flow.service.TaskHandlerService;
import com.finn.flow.service.WorkOrderService;
import com.finn.flow.vo.TaskRecordVO;
import com.finn.flow.vo.WorkOrderVO;
import com.finn.framework.entity.MetaEntity;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.framework.service.JobService;
import com.finn.framework.utils.annotations.Idempotent;
import com.finn.framework.utils.annotations.RequestKeyParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 工单表
 * 实现了JobService接口，可以通过异步接口调用进行工单登记
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-23
 */
@Service(WorkOrderServiceImpl.funcCode)
public class WorkOrderServiceImpl implements WorkOrderService, JobService {

    // 功能号
    public static final String funcCode = "F1003";

    private final String processKey = "Process_demo20231222";

    private final TaskHandlerService taskHandlerService;

    private final ProcessHandlerService processHandlerService;

    private final WorkOrderMapper workOrderMapper;

    private final RedisCache redisCache;


    public WorkOrderServiceImpl(TaskHandlerService taskHandlerService, ProcessHandlerService processHandlerService,
                                WorkOrderMapper workOrderMapper, RedisCache redisCache){
        this.taskHandlerService = taskHandlerService;
        this.processHandlerService = processHandlerService;
        this.workOrderMapper = workOrderMapper;
        this.redisCache = redisCache;
    }


    @Override
    public PageResult<WorkOrderVO> page(WorkOrderQuery query) {
        Page<WorkOrderEntity> page = workOrderMapper.getOrderList(query);
        return new PageResult<>(WorkOrderConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
    }


    @Override
    public void save(WorkOrderVO vo) {
        WorkOrderEntity entity = WorkOrderConvert.INSTANCE.convert(vo);
        if(entity.getOrderCode() == null || entity.getOrderCode().isEmpty()){
            entity.setOrderCode(redisCache.getDayIncrementCode("","oh:order", 5));
        }
        if(vo.getExtendJsonMap() != null){
            entity.setExtendJson(JsonUtils.toJsonString(vo.getExtendJsonMap()));
        }
        workOrderMapper.saveOrder(entity);
        vo.setId(entity.getId());
    }

    @Override
    public void update(WorkOrderVO vo) {
        WorkOrderEntity entity = WorkOrderConvert.INSTANCE.convert(vo);
        workOrderMapper.updateOrderById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            WorkOrderEntity param = new WorkOrderEntity();
            param.setId(id);
            param.setDbStatus(0);
            workOrderMapper.updateOrderById(param);
        });
    }

    @Override
    public WorkOrderEntity getOrderById(Long id) {
        return workOrderMapper.getOrderById(id);
    }

    /**
     * 校验工单参数
     * @param data 数据
     */
    @Override
    public void check(HashDto data) {
        // 判断流程是否部署
        if(!processHandlerService.isPeploy(processKey)){
            throw new ServerException("流程还未部署，请部署");
        }
        WorkOrderVO workOrderVO = JsonUtils.convertValue(data, WorkOrderVO.class);

        System.out.println("参数：" + workOrderVO);
    }

    /**
     * 保存工单信息，启动工作流
     * @param data 数据
     * @return map
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Idempotent(keyPrefix = "flow:order:save")
    public Result<List<TaskRecordVO>> handle(@RequestKeyParam MetaEntity data) throws ServerException {
        WorkOrderVO workOrderVO = JsonUtils.convertValue(data.getData(), WorkOrderVO.class);
        if(workOrderVO.getId() == null || workOrderVO.getId() == 0L){
            workOrderVO.setId(Tools.snowFlakeId());
        }
        // 启动流程
        List<TaskRecordVO> list = taskHandlerService.startByProcessKey(processKey, String.valueOf(workOrderVO.getId()), null);
        if(list != null && !list.isEmpty()){
            List<String> flowInfo = new ArrayList<>(list.size());
            list.forEach(item ->{
                flowInfo.add(item.getProcInstId());
            });
            workOrderVO.getExtendJsonMap().put("procInstIds", flowInfo);
        }
        this.save(workOrderVO);
        // 模拟业务处理异常
//        throw new ServerException("模拟异常！！！");
        return Result.ok(list);
    }
}