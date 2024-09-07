package com.iris.system.base.mapper;

import com.iris.system.base.entity.SysVersionInfoEntity;
import com.iris.system.base.query.SysVersionInfoQuery;
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
public interface SysVersionInfoMapper {

    List<SysVersionInfoEntity> getList(SysVersionInfoQuery query);

    int save(SysVersionInfoEntity param);

    boolean updateById(SysVersionInfoEntity param);

    boolean updateCurVersion(@Param("isCurrVersion")Boolean isCurrVersion);

    SysVersionInfoEntity getById(@Param("id")Long id);
}