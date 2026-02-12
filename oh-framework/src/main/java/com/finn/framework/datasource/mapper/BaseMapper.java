package com.finn.framework.datasource.mapper;

import com.finn.framework.datasource.annotations.Pages;
import com.finn.framework.datasource.service.ModifyProviderService;
import com.finn.framework.datasource.service.SelectProviderService;
import com.finn.framework.datasource.utils.InsertWrapper;
import com.finn.framework.datasource.utils.Wrapper;
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
    long insert(T entity);

    /**
     * 根据ID更新
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
     * 分页查询;需设置当前页和每页数量
     * @param fp 使用QueryWrapper构建
     * @return page
     */
    @Pages
    @SelectProvider(method = SelectProviderService.SELECT_PAGE_PARAM, type = SelectProviderService.class)
    Page<T>  selectPageByWrapper(Wrapper<T> fp);

    /**
     * 查询列表
     * @param fp QueryWrapper
     * @return list
     */
    @SelectProvider(method = SelectProviderService.SELECT_LIST_PARAM, type = SelectProviderService.class)
    List<T> selectListByWrapper(Wrapper<T> fp);

    /**
     * 查询列表
     * @param fp CountWrapper
     * @return list
     */
    @SelectProvider(method = SelectProviderService.SELECT_COUNT_PARAM, type = SelectProviderService.class)
    long count(Wrapper<T> fp);

    /**
     * 根据ID查询数据
     * @param id id主键
     * @param clazz 类名，用于获取表名
     * @return 实体对象
     */
    @SelectProvider(method = SelectProviderService.FIND_BY_ID, type = SelectProviderService.class)
    T findById(@Param("id")Long id,Class<T> clazz);

    /**
     * 删除接口
     * @param fp 使用DeleteWrapper构建
     * @return 删除数量
     */
    @DeleteProvider(method = ModifyProviderService.DELETE_PARAM, type = ModifyProviderService.class)
    Integer deleteByWrapper(Wrapper<T> fp);

    /**
     * 修改数据
     * @param fp 使用UpdateWrapper构建
     * @return 更新数量
     */
    @UpdateProvider(method = ModifyProviderService.UPDATE_PARAM, type = ModifyProviderService.class)
    Integer updateByWrapper(Wrapper<T> fp);

    /**
     * 新增
     * @param fp 使用InsertWrapper构建
     * @return 插入数量
     */
    @InsertProvider(method = ModifyProviderService.INSERT_PARAM, type = ModifyProviderService.class)
    Integer insertByWrapper(InsertWrapper fp);
}
