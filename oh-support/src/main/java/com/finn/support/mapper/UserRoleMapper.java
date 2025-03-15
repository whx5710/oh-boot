package com.finn.support.mapper;

import com.finn.core.constant.Constant;
import com.finn.framework.datasource.annotations.Ds;
import com.finn.support.entity.UserRoleEntity;
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
@Ds(Constant.DYNAMIC_SYS_DB)
public interface UserRoleMapper {

    /**
     * 角色ID列表
     * @param userId  用户ID
     *
     * @return  返回角色ID列表
     */
    @Select("select role_id from sys_user_role where user_id = #{userId} and db_status = 1")
    List<Long> getRoleIdList(@Param("userId") Long userId);

    int saveBatch(@Param("list") List<UserRoleEntity> params);

    boolean deleteByUserIdList(@Param("list") List<Long> userIdList,@Param("param") UserRoleEntity param);

    boolean deleteByRoleIdList(@Param("list") List<Long> roleIdList, @Param("param") UserRoleEntity param);
}