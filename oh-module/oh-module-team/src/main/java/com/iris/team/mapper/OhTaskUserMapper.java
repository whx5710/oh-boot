package com.iris.team.mapper;

import com.iris.framework.datasource.mapper.BaseMapper;
import com.iris.team.entity.OhTaskUserEntity;
import com.iris.team.query.OhTaskUserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 任务人员表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Mapper
public interface OhTaskUserMapper extends BaseMapper<OhTaskUserEntity> {
    List<OhTaskUserEntity> getList(OhTaskUserQuery query);
    int save(OhTaskUserEntity param);
	boolean updateById(OhTaskUserEntity param);
    OhTaskUserEntity getById(@Param("id")Long id);
}