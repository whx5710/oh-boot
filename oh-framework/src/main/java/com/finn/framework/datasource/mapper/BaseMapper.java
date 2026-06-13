package com.finn.framework.datasource.mapper;

import com.finn.framework.datasource.service.ModifyProviderService;
import com.finn.framework.datasource.service.QueryProviderService;
import com.finn.framework.datasource.wrapper.Wrapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;

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
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") // 回写ID
    @InsertProvider(method = ModifyProviderService.INSERT, type = ModifyProviderService.class)
    long insert(T entity);

    /**
     * 批量插入记录
     * @param entities 实体类列表
     * @return int
     */
    @InsertProvider(method = ModifyProviderService.INSERT_BATCH, type = ModifyProviderService.class)
    long insertBatch(List<T> entities);

    /**
     * 根据ID更新，值为null不会进行更新
     * @param entity 实体类
     * @return int
     */
    @UpdateProvider(method = ModifyProviderService.UPDATE, type = ModifyProviderService.class)
    long updateById(T entity);

    /**
     * 删除数据
     * @param entity 实体类
     * @return boolean
     */
    @DeleteProvider(method = ModifyProviderService.DELETE, type = ModifyProviderService.class)
    boolean delete(T entity);

    /**
     * 通用单表查询，如果传入了分页参数，进行分页查询
     * @param queryWrapper QueryWrapper
     * @return list/page
     */
    @SelectProvider(method = QueryProviderService.LIST_PARAM, type = QueryProviderService.class)
    Page<T> listByWrapper(Wrapper<T> queryWrapper);

    /**
     * 查询列表
     * @param countWrapper CountWrapper
     * @return list
     */
    @SelectProvider(method = QueryProviderService.SELECT_COUNT_PARAM, type = QueryProviderService.class)
    long count(Wrapper<T> countWrapper);

    /**
     * 根据ID查询数据
     * @param id id主键
     * @param clazz 类名，用于获取表名
     * @return 实体对象
     */
    @SelectProvider(method = QueryProviderService.FIND_BY_ID, type = QueryProviderService.class)
    T findById(@Param("id") Serializable id, Class<T> clazz);

    /**
     * 删除接口
     * @param deleteWrapper 使用DeleteWrapper构建
     * @return 删除数量
     */
    @DeleteProvider(method = ModifyProviderService.DELETE_PARAM, type = ModifyProviderService.class)
    Integer deleteByWrapper(Wrapper<T> deleteWrapper);

    /**
     * 修改数据，set什么值就更新什么值
     * @param updateWrapper 使用UpdateWrapper构建
     * @return 更新数量
     */
    @UpdateProvider(method = ModifyProviderService.UPDATE_PARAM, type = ModifyProviderService.class)
    Integer updateByWrapper(Wrapper<T> updateWrapper);

}
