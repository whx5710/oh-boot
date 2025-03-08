package com.finn.support.mapper;

import com.finn.core.constant.Constant;
import com.finn.framework.datasource.annotations.Ds;
import com.finn.framework.datasource.service.ModifyProviderService;
import com.finn.support.entity.SysRoleEntity;
import com.finn.support.query.SysRoleQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 角色管理
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
@Ds(Constant.DYNAMIC_SYS_DB)
public interface SysRoleMapper {


    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") // 回写ID
    @InsertProvider(method = ModifyProviderService.INSERT,type = ModifyProviderService.class) // 动态拼接SQL
    int insertRole(SysRoleEntity sysRoleEntity);

    /**
     * 根据用户ID，获取用户最大的数据范围
     */
    @Select("select min(t1.data_scope) from sys_role t1, sys_user_role t2 where t1.id = t2.role_id and t2.user_id = #{userId} and t1.db_status = 1 and t2.db_status = 1")
    Integer getDataScopeByUserId(@Param("userId") Long userId);

    List<SysRoleEntity> getList(SysRoleQuery sysRoleQuery);

    // 动态拼接SQL
    @UpdateProvider(method = ModifyProviderService.UPDATE, type = ModifyProviderService.class)
    boolean updateById(SysRoleEntity sysRoleEntity);

    @Select("select * from sys_role where id = #{id}")
    SysRoleEntity getById(@Param("id") Long id);

}
