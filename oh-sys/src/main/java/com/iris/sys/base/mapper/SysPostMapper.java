package com.iris.sys.base.mapper;

import com.iris.sys.base.entity.SysPostEntity;
import com.iris.sys.base.query.SysPostQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 岗位管理
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface SysPostMapper {

    List<SysPostEntity> getList(SysPostQuery sysPostQuery);

    int insertPost(SysPostEntity sysPostEntity);

    boolean updateById(SysPostEntity sysPostEntity);

    SysPostEntity getById(@Param("id") Long id);
}