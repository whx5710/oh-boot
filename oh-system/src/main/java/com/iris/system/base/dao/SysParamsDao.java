package com.iris.system.base.dao;

import com.iris.framework.common.constant.Constant;
import com.iris.framework.datasource.annotations.Ds;
import com.iris.system.base.entity.SysParamsEntity;
import com.iris.system.base.query.SysParamsQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 参数管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
@Ds(Constant.SYS_DB) // 初始化需缓存
public interface SysParamsDao {

    default boolean isExist(String paramKey) {
        SysParamsQuery query = new SysParamsQuery();
        query.setParamKey(paramKey);
        List<SysParamsEntity> list = getList(query);
        return list != null && !list.isEmpty();
    }

    List<SysParamsEntity> getList(SysParamsQuery query);

    boolean updateById(SysParamsEntity param);

    SysParamsEntity getById(@Param("id")Long id);

    int save(SysParamsEntity param);
}