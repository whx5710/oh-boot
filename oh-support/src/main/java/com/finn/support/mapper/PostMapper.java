package com.finn.support.mapper;

import com.finn.core.constant.Constant;
import com.finn.framework.datasource.annotations.Ds;
import com.finn.framework.datasource.service.ModifyProviderService;
import com.finn.support.entity.PostEntity;
import com.finn.support.query.PostQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* 岗位管理
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
@Ds(Constant.DYNAMIC_SYS_DB)
public interface PostMapper {

    List<PostEntity> getList(PostQuery postQuery);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") // 回写ID
    @InsertProvider(method = ModifyProviderService.INSERT, type = ModifyProviderService.class)
    int insertPost(PostEntity postEntity);

    @UpdateProvider(method = ModifyProviderService.UPDATE, type = ModifyProviderService.class)
    boolean updateById(PostEntity postEntity);

    @Select("select * from sys_post where id = #{id}")
    PostEntity getById(@Param("id") Long id);
}