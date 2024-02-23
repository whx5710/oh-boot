package com.iris.workflow.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iris.framework.common.service.JobService;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.common.utils.ServiceFactory;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.workflow.convert.WorkOrderConvert;
import com.iris.workflow.entity.WorkOrderEntity;
import com.iris.workflow.query.WorkOrderQuery;
import com.iris.workflow.service.TaskHandlerService;
import com.iris.workflow.vo.TaskRecordVO;
import com.iris.workflow.vo.WorkOrderVO;
import com.iris.workflow.dao.WorkOrderDao;
import com.iris.workflow.service.WorkOrderService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工单表
 * 实现了JobService接口，可以通过异步接口调用进行工单登记
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-23
 */
@Service
public class WorkOrderServiceImpl extends BaseServiceImpl<WorkOrderDao, WorkOrderEntity> implements WorkOrderService, JobService, InitializingBean {

    private final TaskHandlerService taskHandlerService;

    public WorkOrderServiceImpl(TaskHandlerService taskHandlerService){
        this.taskHandlerService = taskHandlerService;
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
     * @param data
     */
    @Override
    public void check(JSONObject data) {
        WorkOrderVO workOrderVO = JSONUtil.toBean(data, WorkOrderVO.class);
        System.out.println(workOrderVO);
    }

    /**
     * 保存工单信息，启动工作流
     * @param data
     * @return
     */
    @Override
    public JSONObject handle(JSONObject data) {
        WorkOrderVO workOrderVO = JSONUtil.toBean(data, WorkOrderVO.class);
        this.save(workOrderVO);

        // 启动流程
        List<TaskRecordVO> list = taskHandlerService.startByProcessKey("Process_demo20231222", String.valueOf(workOrderVO.getId()), null);
        JSONObject object = new JSONObject();
        object.set("msg","ok");
        object.set("data", list);
        return object;
    }

    /**
     * 注册服务
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ServiceFactory.register("F1003", this); // 保存工单，启动流程业务
    }
}