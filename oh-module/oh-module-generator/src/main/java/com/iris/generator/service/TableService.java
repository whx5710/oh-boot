package com.iris.generator.service;

import com.iris.generator.common.service.BaseService;
import com.iris.generator.entity.TableEntity;
import com.iris.generator.common.page.PageResult;
import com.iris.generator.common.query.Query;

/**
 * 数据表
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface TableService extends BaseService<TableEntity> {

    PageResult<TableEntity> page(Query query);

    TableEntity getByTableName(String tableName);

    void deleteBatchIds(Long[] ids);

    /**
     * 导入表
     *
     * @param datasourceId 数据源ID
     * @param tableName    表名
     */
    void tableImport(Long datasourceId, String tableName);

    /**
     * 同步数据库表
     *
     * @param id 表ID
     */
    void sync(Long id);
}