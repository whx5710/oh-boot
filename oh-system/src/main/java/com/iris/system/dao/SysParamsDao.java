package com.iris.system.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iris.system.entity.SysParamsEntity;
import com.iris.framework.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 参数管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface SysParamsDao extends BaseDao<SysParamsEntity> {

    default boolean isExist(String paramKey) {
        return this.exists(new QueryWrapper<SysParamsEntity>().eq("param_key" , paramKey));
    }
}