package com.finn.team.mapper;

import com.finn.framework.datasource.annotations.Pages;
import com.finn.team.entity.OhProjectEntity;
import com.finn.team.query.OhProjectQuery;
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
public interface OhProjectMapper {

    @Pages
    List<OhProjectEntity> getList(OhProjectQuery query);

    int save(OhProjectEntity param);

    boolean updateById(OhProjectEntity param);

    OhProjectEntity getById(@Param("id")Long id);
}