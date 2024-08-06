package com.iris.team.dao;

import com.iris.team.entity.OhProjectEntity;
import com.iris.team.query.OhProjectQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 项目信息表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2022-11-25
*/
@Mapper
public interface OhProjectDao {
    List<OhProjectEntity> getList(OhProjectQuery query);

    int save(OhProjectEntity param);

    boolean updateById(OhProjectEntity param);

    OhProjectEntity getById(@Param("id")Long id);
}