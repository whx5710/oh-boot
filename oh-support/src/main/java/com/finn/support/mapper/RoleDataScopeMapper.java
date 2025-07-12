package com.finn.support.mapper;

import com.finn.core.constant.Constant;
import com.finn.framework.datasource.annotations.Ds;
import com.finn.support.entity.RoleDataScopeEntity;
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
@Ds(Constant.DYNAMIC_SYS_DB)
public interface RoleDataScopeMapper {

    /**
     * 根据角色ID，获取部门ID列表
     */
    List<Long> getDeptIdList(@Param("roleId") Long roleId);

    /**
     * 获取用户的数据权限列表
     */
    List<Long> getDataScopeList(@Param("userId") Long userId);

    int saveBatch(@Param("list") List<RoleDataScopeEntity> param);

    int deleteByRoleIdList(@Param("list") List<Long> roleIdList, @Param("param") RoleDataScopeEntity param);

    int deleteDeptIdList(@Param("list") List<Long> deptIdList, @Param("param") RoleDataScopeEntity param);
}