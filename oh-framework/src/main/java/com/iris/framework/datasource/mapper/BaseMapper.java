package com.iris.framework.datasource.mapper;

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
//    @InsertProvider(method = "insert", type = ProviderService.class)
//    int insert(T entity);

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
//    @SelectProvider(method = "selectById", type = ProviderService.class)
//    T selectById(@Param("id") Long id);

}
