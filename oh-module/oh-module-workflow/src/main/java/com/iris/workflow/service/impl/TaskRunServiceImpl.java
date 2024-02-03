package com.iris.workflow.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.workflow.entity.TaskRunEntity;
import com.iris.workflow.query.TaskRunQuery;
import com.iris.workflow.dao.TaskRunDao;
import com.iris.workflow.service.TaskRunService;
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
@AllArgsConstructor
public class TaskRunServiceImpl extends BaseServiceImpl<TaskRunDao, TaskRunEntity> implements TaskRunService {

    @Override
    public PageResult<TaskRunEntity> page(TaskRunQuery query) {
        IPage<TaskRunEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    private LambdaQueryWrapper<TaskRunEntity> getWrapper(TaskRunQuery query){
        LambdaQueryWrapper<TaskRunEntity> wrapper = Wrappers.lambdaQuery();

        return wrapper;
    }

    @Override
    public boolean save(TaskRunEntity vo) {
        baseMapper.insert(vo);
        return true;
    }

    @Override
    public void update(TaskRunEntity vo) {

        updateById(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}