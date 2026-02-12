package com.finn.system.mapper;

import com.finn.framework.datasource.annotations.Pages;
import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.system.entity.VersionInfoEntity;
import com.finn.system.query.VersionInfoQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 版本信息
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-09-16
*/
@Mapper
public interface VersionInfoMapper extends BaseMapper<VersionInfoEntity> {

    @Pages
    List<VersionInfoEntity> getList(VersionInfoQuery query);

    int save(VersionInfoEntity param);

    //boolean updateById(VersionInfoEntity param);

    boolean updateCurVersion(@Param("isCurrVersion")Boolean isCurrVersion);

    VersionInfoEntity getById(@Param("id")Long id);
}