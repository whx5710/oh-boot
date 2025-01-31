package com.iris.team.mapper;

import com.iris.framework.datasource.mapper.BaseMapper;
import com.iris.team.entity.OhProjectLogEntity;
import com.iris.team.query.OhProjectLogQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 项目、任务操作日志
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Mapper
public interface OhProjectLogMapper extends BaseMapper<OhProjectLogEntity> {

    List<OhProjectLogEntity> getList(OhProjectLogQuery query);

    int save(OhProjectLogEntity param);

    int updateById(OhProjectLogEntity param);

    OhProjectLogEntity getById(@Param("id")Long id);
}