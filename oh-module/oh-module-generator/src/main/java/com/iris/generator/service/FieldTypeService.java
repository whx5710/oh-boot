package com.iris.generator.service;

import com.iris.generator.common.service.BaseService;
import com.iris.generator.entity.FieldTypeEntity;
import com.iris.generator.common.page.PageResult;
import com.iris.generator.common.query.Query;

import java.util.Map;
import java.util.Set;

/**
 * 字段类型管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface FieldTypeService extends BaseService<FieldTypeEntity> {
    PageResult<FieldTypeEntity> page(Query query);

    Map<String, FieldTypeEntity> getMap();

    /**
     * 根据tableId，获取包列表
     *
     * @param tableId 表ID
     * @return 返回包列表
     */
    Set<String> getPackageByTableId(Long tableId);

    Set<String> getList();
}