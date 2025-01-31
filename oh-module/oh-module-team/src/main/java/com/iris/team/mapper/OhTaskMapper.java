package com.iris.team.mapper;

import com.iris.framework.datasource.mapper.BaseMapper;
import com.iris.team.entity.OhTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* 任务表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Mapper
public interface OhTaskMapper extends BaseMapper<OhTaskEntity> {

    List<OhTaskEntity> getList(Map<String, Object> params);

    int save(OhTaskEntity param);

    int updateById(OhTaskEntity param);

    OhTaskEntity getById(@Param("id")Long id);
}