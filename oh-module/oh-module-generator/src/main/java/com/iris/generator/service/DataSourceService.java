package com.iris.generator.service;

import com.iris.generator.common.service.BaseService;
import com.iris.generator.config.GenDataSource;
import com.iris.generator.entity.DataSourceEntity;
import com.iris.generator.common.page.PageResult;
import com.iris.generator.common.query.Query;

import java.util.List;

/**
 * 数据源管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface DataSourceService extends BaseService<DataSourceEntity> {

    PageResult<DataSourceEntity> page(Query query);

    List<DataSourceEntity> getList();

    /**
     * 获取数据库产品名，如：MySQL
     *
     * @param datasourceId 数据源ID
     * @return 返回产品名
     */
    String getDatabaseProductName(Long datasourceId);

    /**
     * 根据数据源ID，获取数据源
     *
     * @param datasourceId 数据源ID
     */
    GenDataSource get(Long datasourceId);
}