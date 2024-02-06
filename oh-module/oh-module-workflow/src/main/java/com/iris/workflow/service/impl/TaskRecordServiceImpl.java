package com.iris.workflow.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iris.workflow.convert.TaskRecordConvert;
import com.iris.workflow.entity.TaskRecordEntity;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.workflow.query.TaskRecordQuery;
import com.iris.workflow.dao.TaskRecordDao;
import com.iris.workflow.service.TaskRecordService;
import com.iris.workflow.vo.TaskRecordVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 环节运行表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-03
 */
@Service
public class TaskRecordServiceImpl extends BaseServiceImpl<TaskRecordDao, TaskRecordEntity> implements TaskRecordService {

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
    public void update(TaskRecordEntity vo) {

        updateById(vo);
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
     * 获取活动中未完成的任务
     * @param procInstId
     * @return
     */
    @Override
    public List<TaskRecordVO> activityTask(String procInstId) {
        LambdaQueryWrapper<TaskRecordEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TaskRecordEntity::getDeleted, 0).eq(TaskRecordEntity::getProcInstId, procInstId)
                .eq(TaskRecordEntity::getRunMark, 1);
        return TaskRecordConvert.INSTANCE.convertList(baseMapper.selectList(wrapper));
    }

}