package com.iris.system.pim.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iris.framework.common.constant.Constant;
import com.iris.system.pim.entity.SysParamsEntity;
import com.iris.framework.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 参数管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
@DS(Constant.SYS_DB)
public interface SysParamsDao extends BaseDao<SysParamsEntity> {

    default boolean isExist(String paramKey) {
        return this.exists(new QueryWrapper<SysParamsEntity>().eq("param_key" , paramKey));
    }
}