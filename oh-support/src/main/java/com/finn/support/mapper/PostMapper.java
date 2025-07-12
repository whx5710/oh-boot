package com.finn.support.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.framework.datasource.service.ModifyProviderService;
import com.finn.support.entity.PostEntity;
import org.apache.ibatis.annotations.*;

/**
* 岗位管理
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface PostMapper extends BaseMapper<PostEntity> {

//    List<PostEntity> getList(PostQuery postQuery);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") // 回写ID
    @InsertProvider(method = ModifyProviderService.INSERT, type = ModifyProviderService.class)
    int insertPost(PostEntity postEntity);

    @Select("select * from sys_post where id = #{id}")
    PostEntity getById(@Param("id") Long id);
}