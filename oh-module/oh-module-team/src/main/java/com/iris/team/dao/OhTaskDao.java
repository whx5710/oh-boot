package com.iris.team.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.team.entity.OhTaskEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* 任务表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Mapper
@DS(Constant.PROJECT_DB)
public interface OhTaskDao extends BaseDao<OhTaskEntity> {

    List<OhTaskEntity> getList(Map<String, Object> params);
}