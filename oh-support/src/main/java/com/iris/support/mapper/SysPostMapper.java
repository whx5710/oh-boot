package com.iris.support.mapper;

import com.iris.core.constant.Constant;
import com.iris.framework.datasource.annotations.Ds;
import com.iris.framework.datasource.service.ProviderService;
import com.iris.support.entity.SysPostEntity;
import com.iris.support.query.SysPostQuery;
import org.apache.ibatis.annotations.*;

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

    @InsertProvider(method = ProviderService.INSERT, type = ProviderService.class)
    int insertPost(SysPostEntity sysPostEntity);

    @UpdateProvider(method = ProviderService.UPDATE, type = ProviderService.class)
    boolean updateById(SysPostEntity sysPostEntity);

    @Select("select * from sys_post where id = #{id}")
    SysPostEntity getById(@Param("id") Long id);
}