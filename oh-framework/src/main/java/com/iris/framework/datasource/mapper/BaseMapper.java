package com.iris.framework.datasource.mapper;


import com.iris.framework.datasource.service.IrisProvider;
import org.apache.ibatis.annotations.InsertProvider;

/**
 * 基础Mapper，集合公共接口
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface BaseMapper<T>{

    /**
     * 插入一条记录
     * @param entity 实体类
     * @return int
     */
    @InsertProvider(method = "insertEntity", type = IrisProvider.class)
    int insert(T entity);

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
//    T selectById(@Param("id") Long id);

}
