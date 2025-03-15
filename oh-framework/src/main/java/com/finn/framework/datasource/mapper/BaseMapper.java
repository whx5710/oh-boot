package com.finn.framework.datasource.mapper;

import com.finn.framework.datasource.annotations.Pages;
import com.finn.framework.datasource.service.ModifyProviderService;
import com.finn.framework.datasource.service.SelectProviderService;
import com.finn.framework.query.Query;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

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
    int insert(T entity);

    /**
     * 根据ID更新
     * @param entity 实体类
     * @return int
     */
    @UpdateProvider(method = ModifyProviderService.UPDATE, type = ModifyProviderService.class)
    int updateById(T entity);

    /**
     * 删除数据
     * @param entity 实体类
     * @return boolean
     */
    @DeleteProvider(method = ModifyProviderService.DELETE, type = ModifyProviderService.class)
    boolean delete(T entity);

    /**
     * 查询列表
     * @param entry 实体类
     * @return list
     */
    @SelectProvider(method = SelectProviderService.SELECT_LIST, type = SelectProviderService.class)
    List<T> selectList(T entry);

    /**
     * 分页查询
     * @param query 参数
     * @param clazz 类别
     * @return list
     */
    @Pages
    @SelectProvider(method = SelectProviderService.SELECT_PAGE, type = SelectProviderService.class)
    Page<T> selectPage(@Param("query") Query query, @Param("clazz")Class<T> clazz);
}
