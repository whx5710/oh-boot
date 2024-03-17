package com.iris.flow.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.flow.entity.TaskRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 环节运行表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2024-02-03
*/
@Mapper
@DS(Constant.PROJECT_DB)
public interface TaskRecordDao extends BaseDao<TaskRecordEntity> {

    // 修改当前运行标志
    boolean updateRunMark(@Param("procInstId") String procInstId);
}