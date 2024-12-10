package com.iris.support.mapper;

import com.iris.support.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色关系
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface SysUserRoleMapper {

    /**
     * 角色ID列表
     * @param userId  用户ID
     *
     * @return  返回角色ID列表
     */
    List<Long> getRoleIdList(@Param("userId") Long userId);

    int saveBatch(@Param("list") List<SysUserRoleEntity> params);

    boolean deleteByUserIdList(@Param("list") List<Long> userIdList,@Param("param")SysUserRoleEntity param);

    boolean deleteByRoleIdList(@Param("list") List<Long> roleIdList, @Param("param")SysUserRoleEntity param);
}