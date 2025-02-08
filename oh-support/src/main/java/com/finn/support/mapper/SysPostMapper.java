package com.finn.support.mapper;

import com.finn.core.constant.Constant;
import com.finn.framework.datasource.annotations.Ds;
import com.finn.framework.datasource.service.ProviderService;
import com.finn.support.entity.SysPostEntity;
import com.finn.support.query.SysPostQuery;
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

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") // 回写ID
    @InsertProvider(method = ProviderService.INSERT, type = ProviderService.class)
    int insertPost(SysPostEntity sysPostEntity);

    @UpdateProvider(method = ProviderService.UPDATE, type = ProviderService.class)
    boolean updateById(SysPostEntity sysPostEntity);

    @Select("select * from sys_post where id = #{id}")
    SysPostEntity getById(@Param("id") Long id);
}