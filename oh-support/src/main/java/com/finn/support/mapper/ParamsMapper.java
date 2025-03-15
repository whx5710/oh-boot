package com.finn.support.mapper;

import com.finn.core.constant.Constant;
import com.finn.framework.datasource.annotations.Ds;
import com.finn.support.entity.ParamsEntity;
import com.finn.support.query.ParamsQuery;
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
@Ds(Constant.DYNAMIC_SYS_DB)
public interface ParamsMapper {

    default boolean isExist(String paramKey) {
        ParamsQuery query = new ParamsQuery();
        query.setParamKey(paramKey);
        List<ParamsEntity> list = getList(query);
        return list != null && !list.isEmpty();
    }

    List<ParamsEntity> getList(ParamsQuery query);

    boolean updateById(ParamsEntity param);

    ParamsEntity getById(@Param("id")Long id);

    int save(ParamsEntity param);
}