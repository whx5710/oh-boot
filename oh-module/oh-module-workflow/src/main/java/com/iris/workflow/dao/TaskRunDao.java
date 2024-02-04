package com.iris.workflow.dao;

import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.workflow.entity.TaskRunEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 环节运行表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-02-03
*/
@Mapper
public interface TaskRunDao extends BaseDao<TaskRunEntity> {

    // 修改当前运行标志
    boolean updateRunMark(@Param("procInstId") String procInstId);
}