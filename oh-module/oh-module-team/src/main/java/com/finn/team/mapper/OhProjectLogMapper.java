package com.finn.team.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.team.entity.OhProjectLogEntity;
import com.finn.team.query.OhProjectLogQuery;
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