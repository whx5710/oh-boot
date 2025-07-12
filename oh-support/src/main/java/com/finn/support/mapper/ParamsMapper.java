package com.finn.support.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.framework.datasource.utils.QueryWrapper;
import com.finn.support.entity.ParamsEntity;
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
public interface ParamsMapper extends BaseMapper<ParamsEntity> {

    default boolean isExist(String paramKey) {
        List<ParamsEntity> list = selectListByWrapper(QueryWrapper.of(ParamsEntity.class)
                .eq(ParamsEntity::getDbStatus, 1).eq(ParamsEntity::getParamKey, paramKey));
        return list != null && !list.isEmpty();
    }

//    List<ParamsEntity> getList(ParamsQuery query);

    ParamsEntity getById(@Param("id")Long id);

//    int save(ParamsEntity param);
}