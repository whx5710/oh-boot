package com.iris.support.mapper;

import com.iris.core.constant.Constant;
import com.iris.framework.datasource.annotations.Ds;
import com.iris.support.entity.SysUserPostEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 用户岗位关系
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
@Ds(Constant.DYNAMIC_SYS_DB)
public interface SysUserPostMapper {

    /**
     * 岗位ID列表
     * @param userId  用户ID
     */
    List<Long> getPostIdList(@Param("userId") Long userId);

    int saveBatch(@Param("list") List<SysUserPostEntity> param);

    boolean deleteByUserIdList(@Param("list")List<Long> userIdList, @Param("param")SysUserPostEntity param);

    boolean deleteByPostIdList(@Param("list")List<Long> postIdList, @Param("param")SysUserPostEntity param);
}