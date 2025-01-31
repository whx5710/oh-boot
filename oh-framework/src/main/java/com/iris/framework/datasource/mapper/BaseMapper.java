package com.iris.framework.datasource.mapper;

import com.iris.framework.datasource.service.ProviderService;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.UpdateProvider;

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
    @InsertProvider(method = ProviderService.INSERT, type = ProviderService.class)
    int insert(T entity);

    /**
     * 根据ID更新
     * @param entity 实体类
     * @return int
     */
    @UpdateProvider(method = ProviderService.UPDATE, type = ProviderService.class)
    int updateById(T entity);

    /**
     * 删除数据
     * @param entity 实体类
     * @return boolean
     */
    @DeleteProvider(method = ProviderService.DELETE, type = ProviderService.class)
    boolean delete(T entity);
}
