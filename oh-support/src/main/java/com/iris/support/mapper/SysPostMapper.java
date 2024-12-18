package com.iris.support.mapper;

import com.iris.core.constant.Constant;
import com.iris.framework.datasource.annotations.Ds;
import com.iris.support.entity.SysPostEntity;
import com.iris.support.query.SysPostQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* 岗位管理
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
@Ds(Constant.DYNAMIC_SYS_DB)
public interface SysPostMapper {

    List<SysPostEntity> getList(SysPostQuery sysPostQuery);

    int insertPost(SysPostEntity sysPostEntity);

    boolean updateById(SysPostEntity sysPostEntity);

    @Select("select * from sys_post where id = #{id}")
    SysPostEntity getById(@Param("id") Long id);
}