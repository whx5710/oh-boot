package com.finn.system.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.system.entity.PostEntity;
import org.apache.ibatis.annotations.*;

/**
* 岗位管理
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface PostMapper extends BaseMapper<PostEntity> {

    @Select("select * from sys_post where id = #{id}")
    PostEntity getById(@Param("id") Long id);
}