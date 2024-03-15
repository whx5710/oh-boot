package com.iris.system.pim.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.framework.common.constant.Constant;
import com.iris.system.pim.entity.SysRoleEntity;
import com.iris.framework.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 角色管理
 * 
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
@DS(Constant.SYS_DB)
public interface SysRoleDao extends BaseDao<SysRoleEntity> {

    /**
     * 根据用户ID，获取用户最大的数据范围
     */
    Integer getDataScopeByUserId(@Param("userId") Long userId);

}
