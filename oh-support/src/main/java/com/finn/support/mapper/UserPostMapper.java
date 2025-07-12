package com.finn.support.mapper;

import com.finn.support.entity.UserPostEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* 用户岗位关系
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface UserPostMapper {

    /**
     * 岗位ID列表
     * @param userId  用户ID
     */
    @Select("select post_id from sys_user_post where user_id = #{userId} and db_status = 1")
    List<Long> getPostIdList(@Param("userId") Long userId);

    int saveBatch(@Param("list") List<UserPostEntity> param);

    boolean deleteByUserIdList(@Param("list")List<Long> userIdList, @Param("param") UserPostEntity param);

    boolean deleteByPostIdList(@Param("list")List<Long> postIdList, @Param("param") UserPostEntity param);
}