package com.finn.system.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.system.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户角色关系
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {

    /**
     * 角色ID列表
     * @param userId  用户ID
     *
     * @return  返回角色ID列表
     */
    @Select("select role_id from sys_user_role where user_id = #{userId} and db_status = 1")
    List<Long> getRoleIdList(@Param("userId") Long userId);

    /**
     * 根据用户ID获取角色ID
     * @param userId 用户ID
     * @return 角色ids
     */
    @Select("select sys_user_role.role_id from sys_user_role INNER JOIN sys_role " +
            "on sys_user_role.role_id = sys_role.id and sys_user_role.db_status = 1 " +
            "and sys_role.db_status = 1 and sys_user_role.user_id = #{userId}")
    List<Long> getTenantRoleIds(@Param("userId") Long userId);

    /**
     * 查询角色权限列表
     * @param roleIds  角色ID
     */
    List<String> getUserAuthorityList(@Param("roleIds") List<Long> roleIds);

    /**
     * 查询所有权限列表
     */
    List<String> getAuthorityList();
}