package com.iris.system.pim.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.system.pim.entity.SysUserRoleEntity;
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
@DS(Constant.SYS_DB)
public interface SysUserRoleDao extends BaseDao<SysUserRoleEntity> {

    /**
     * 角色ID列表
     * @param userId  用户ID
     *
     * @return  返回角色ID列表
     */
    List<Long> getRoleIdList(@Param("userId") Long userId);
}