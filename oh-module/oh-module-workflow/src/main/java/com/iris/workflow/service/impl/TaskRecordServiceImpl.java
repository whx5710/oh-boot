package com.iris.workflow.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iris.framework.common.utils.AssertUtils;
import com.iris.framework.security.user.SecurityUser;
import com.iris.workflow.convert.TaskRecordConvert;
import com.iris.workflow.entity.TaskRecordEntity;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.workflow.query.TaskRecordQuery;
import com.iris.workflow.dao.TaskRecordDao;
import com.iris.workflow.service.TaskRecordService;
import com.iris.workflow.vo.TaskRecordVO;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 环节运行表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-03
 */
@Service
public class TaskRecordServiceImpl extends BaseServiceImpl<TaskRecordDao, TaskRecordEntity> implements TaskRecordService {

    private final HistoryService historyService;

    public TaskRecordServiceImpl(HistoryService historyService){
        this.historyService = historyService;
    }

    @Override
    public PageResult<TaskRecordEntity> page(TaskRecordQuery query) {
        IPage<TaskRecordEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    private LambdaQueryWrapper<TaskRecordEntity> getWrapper(TaskRecordQuery query){
        LambdaQueryWrapper<TaskRecordEntity> wrapper = Wrappers.lambdaQuery();

        return wrapper;
    }

    @Override
    public boolean save(TaskRecordEntity vo) {
        baseMapper.insert(vo);
        return true;
    }

    @Override
    public void update(TaskRecordVO vo) {
        updateById(TaskRecordConvert.INSTANCE.convert(vo));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    // 修改当前运行标志
    @Override
    public boolean updateRunMark(String procInstId) {
        return this.baseMapper.updateRunMark(procInstId);
    }


    /**
     * 获取任务列表
     * @param query
     * @return
     */
    @Override
    public List<TaskRecordVO> taskList(TaskRecordQuery query) {
        LambdaQueryWrapper<TaskRecordEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TaskRecordEntity::getDeleted, 0);
        if(!ObjectUtils.isEmpty(query.getProcInstId())){
            wrapper.eq(TaskRecordEntity::getProcInstId, query.getProcInstId());
        }
        if(query.getRunMark() != null){
            wrapper.eq(TaskRecordEntity::getRunMark, query.getRunMark());
        }
        if(!ObjectUtils.isEmpty(query.getTaskId())){
            wrapper.eq(TaskRecordEntity::getTaskId, query.getTaskId());
        }
        if(!ObjectUtils.isEmpty(query.getActInstId())){
            wrapper.eq(TaskRecordEntity::getActInstId, query.getActInstId());
        }
        return TaskRecordConvert.INSTANCE.convertList(baseMapper.selectList(wrapper));
    }


    /**
     * 保存环节运行记录表，在执行完 complete或启动流程 后再执行该方法
     * 1、更新上个任务的数据
     * 2、新增当前任务
     * @param proInsId 环节实例ID
     * @param taskId 上一环节任务ID
     * @return
     */
    @Override
    public List<TaskRecordVO> saveTaskRecord(String proInsId, String taskId){
        AssertUtils.isBlank(proInsId, "【参数异常】流程实例ID");
        // 正在运行的环节列表（未完成的任务）
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(proInsId).unfinished()
                .orderByHistoricActivityInstanceStartTime().asc()
                .orderByHistoricTaskInstanceEndTime().asc().list();
        List<TaskRecordVO> data = new ArrayList<>();
        if(!ObjectUtils.isEmpty(list)){
            for(HistoricTaskInstance his: list){
                TaskRecordEntity taskRecord = new TaskRecordEntity();
                taskRecord.setProcDefId(his.getProcessDefinitionId());
                taskRecord.setProcInstId(his.getProcessInstanceId());
                taskRecord.setActInstId(his.getActivityInstanceId());
                taskRecord.setTaskId(his.getId());
                taskRecord.setTaskDefId(his.getTaskDefinitionKey());
                taskRecord.setTaskName(his.getName());

                // 当前标识，默认0，1标识当前环节
                taskRecord.setRunMark((his.getEndTime()!=null && his.getDeleteReason()!= null && his.getDeleteReason().equalsIgnoreCase("completed"))?0:1);
                taskRecord.setAssignee(his.getAssignee());
                taskRecord.setAssigneeName(SecurityUser.getUser().getRealName());
                taskRecord.setStartTime(his.getStartTime());
                taskRecord.setEndTime(his.getEndTime());
                taskRecord.setDuration(his.getDurationInMillis());

                taskRecord.setFromActInstId("");
                taskRecord.setFromTaskDefId("");
                taskRecord.setFromTaskId("");
                taskRecord.setFromTaskName("");

                if(!ObjectUtils.isEmpty(taskId) && !taskId.equals(his.getId())){
                    // 处理上一任务，更新相关信息
                    List<HistoricTaskInstance> parentList = historyService.createHistoricTaskInstanceQuery()
                            .processInstanceId(proInsId).taskId(taskId).orderByHistoricActivityInstanceStartTime().desc()
                            .orderByHistoricTaskInstanceEndTime().desc().list();
                    if(!ObjectUtils.isEmpty(parentList)){
                        for(HistoricTaskInstance hisParent: parentList){
                            taskRecord.setFromActInstId(hisParent.getActivityInstanceId());
                            taskRecord.setFromTaskDefId(hisParent.getTaskDefinitionKey());
                            taskRecord.setFromTaskId(hisParent.getId());
                            taskRecord.setFromTaskName(hisParent.getName());

                            /************ 更新上一任务 *** start *********/
                            TaskRecordQuery query = new TaskRecordQuery();
                            query.setProcInstId(proInsId);
                            query.setRunMark(1);
                            if(!ObjectUtils.isEmpty(taskId)){
                                query.setTaskId(taskId);
                            }
                            List<TaskRecordVO> voList = taskList(query);
                            if(!ObjectUtils.isEmpty(voList)){
                                for (TaskRecordVO vo : voList){
                                    vo.setEndTime(hisParent.getEndTime());
                                    vo.setAssignee(hisParent.getAssignee());
                                    vo.setAssigneeName("");
                                    vo.setDuration(hisParent.getDurationInMillis());
                                    vo.setRunMark(0);
                                    update(vo);
                                }
                            }
                            /********** 更新上一任务 *** end *********/
                            data.add(TaskRecordConvert.INSTANCE.convert(taskRecord));
                            save(taskRecord);
                        }
                    }else{
                        data.add(TaskRecordConvert.INSTANCE.convert(taskRecord));
                        save(taskRecord);
                    }
                }else{
                    data.add(TaskRecordConvert.INSTANCE.convert(taskRecord));
                    save(taskRecord);
                }
            }
        }else{
            TaskRecordEntity taskRecord = new TaskRecordEntity();
            // 处理上一任务，更新相关信息
            List<HistoricTaskInstance> parentList = historyService.createHistoricTaskInstanceQuery()
                    .processInstanceId(proInsId).taskId(taskId).orderByHistoricActivityInstanceStartTime().desc()
                    .orderByHistoricTaskInstanceEndTime().desc().list();
            if(!ObjectUtils.isEmpty(parentList)){
                for(HistoricTaskInstance hisParent: parentList){
                    taskRecord.setFromActInstId(hisParent.getActivityInstanceId());
                    taskRecord.setFromTaskDefId(hisParent.getTaskDefinitionKey());
                    taskRecord.setFromTaskId(hisParent.getId());
                    taskRecord.setFromTaskName(hisParent.getName());

                    // 开始时间用上一任务的结束时间
                    taskRecord.setStartTime(hisParent.getEndTime());
                    taskRecord.setEndTime(hisParent.getEndTime());

                    taskRecord.setProcDefId(hisParent.getProcessDefinitionId());

                    /************ 更新上一任务 *** start *********/
                    TaskRecordQuery query = new TaskRecordQuery();
                    query.setProcInstId(proInsId);
                    query.setRunMark(1);
                    if(!ObjectUtils.isEmpty(taskId)){
                        query.setTaskId(taskId);
                    }
                    List<TaskRecordVO> voList = taskList(query);
                    if(!ObjectUtils.isEmpty(voList)){
                        for (TaskRecordVO vo : voList){
                            vo.setEndTime(hisParent.getEndTime());
                            vo.setAssignee(hisParent.getAssignee());
                            vo.setAssigneeName("");
                            vo.setDuration(hisParent.getDurationInMillis());
                            vo.setRunMark(0);
                            update(vo);
                        }
                    }
                    /********** 更新上一任务 *** end *********/
                    data.add(TaskRecordConvert.INSTANCE.convert(taskRecord));
                    save(taskRecord);
                }
            }else{
                data.add(TaskRecordConvert.INSTANCE.convert(taskRecord));
                save(taskRecord);
            }
        }

        return data;
    }
}