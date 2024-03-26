package com.iris.flow.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iris.framework.common.entity.MetaEntity;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.service.JobService;
import com.iris.framework.common.utils.JsonUtils;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.common.utils.Result;
import com.iris.framework.common.utils.ServiceFactory;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.flow.convert.WorkOrderConvert;
import com.iris.flow.entity.WorkOrderEntity;
import com.iris.flow.query.WorkOrderQuery;
import com.iris.flow.service.ProcessHandlerService;
import com.iris.flow.service.TaskHandlerService;
import com.iris.flow.vo.TaskRecordVO;
import com.iris.flow.vo.WorkOrderVO;
import com.iris.flow.dao.WorkOrderDao;
import com.iris.flow.service.WorkOrderService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 工单表
 * 实现了JobService接口，可以通过异步接口调用进行工单登记
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-23
 */
@Service
public class WorkOrderServiceImpl extends BaseServiceImpl<WorkOrderDao, WorkOrderEntity> implements WorkOrderService, JobService, InitializingBean {

    private final String processKey = "Process_demo20231222";
    private final TaskHandlerService taskHandlerService;

    private final ProcessHandlerService processHandlerService;


    public WorkOrderServiceImpl(TaskHandlerService taskHandlerService, ProcessHandlerService processHandlerService){
        this.taskHandlerService = taskHandlerService;
        this.processHandlerService = processHandlerService;
    }


    @Override
    public PageResult<WorkOrderVO> page(WorkOrderQuery query) {
        IPage<WorkOrderEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(WorkOrderConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<WorkOrderEntity> getWrapper(WorkOrderQuery query){
        LambdaQueryWrapper<WorkOrderEntity> wrapper = Wrappers.lambdaQuery();

        return wrapper;
    }

    @Override
    public void save(WorkOrderVO vo) {
        WorkOrderEntity entity = WorkOrderConvert.INSTANCE.convert(vo);
        baseMapper.insert(entity);
        vo.setId(entity.getId());
    }

    @Override
    public void update(WorkOrderVO vo) {
        WorkOrderEntity entity = WorkOrderConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    /**
     * 校验工单参数
     * @param data 数据
     */
    @Override
    public void check(Map<String, Object> data) {
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
    public Result<List<TaskRecordVO>> handle(MetaEntity data) throws ServerException {
//        JsonUtils.parseObject()
        WorkOrderVO workOrderVO = JsonUtils.convertValue(data.getData(), WorkOrderVO.class);
        this.save(workOrderVO);

        // 启动流程
        List<TaskRecordVO> list = taskHandlerService.startByProcessKey(processKey, String.valueOf(workOrderVO.getId()), null);

        // 模拟业务处理异常
        throw new ServerException("模拟异常！！！");


//        return Result.ok(list);
    }

    /**
     * 注册服务
     * @throws Exception e
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ServiceFactory.register("F1003", this); // 保存工单，启动流程业务
    }
}