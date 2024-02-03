package com.iris.workflow.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;
import com.iris.workflow.entity.TaskRunEntity;
import com.iris.workflow.query.TaskRunQuery;

import java.util.List;

/**
 * 环节运行表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-02-03
 */
public interface TaskRunService extends BaseService<TaskRunEntity> {

    PageResult<TaskRunEntity> page(TaskRunQuery query);

    boolean save(TaskRunEntity vo);

    void update(TaskRunEntity vo);

    void delete(List<Long> idList);
}