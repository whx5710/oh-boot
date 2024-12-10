package com.iris.support.mapper;

import com.iris.support.entity.SysRoleDataScopeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色数据权限
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface SysRoleDataScopeMapper {

    /**
     * 根据角色ID，获取机构ID列表
     */
    List<Long> getOrgIdList(@Param("roleId") Long roleId);

    /**
     * 获取用户的数据权限列表
     */
    List<Long> getDataScopeList(@Param("userId") Long userId);

    int saveBatch(@Param("list") List<SysRoleDataScopeEntity> param);

    int deleteByRoleIdList(@Param("list") List<Long> roleIdList, @Param("param")SysRoleDataScopeEntity param);

    int deleteOrgIdList(@Param("list") List<Long> orgIdList, @Param("param")SysRoleDataScopeEntity param);
}