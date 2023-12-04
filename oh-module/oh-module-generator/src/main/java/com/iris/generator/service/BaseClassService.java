package com.iris.generator.service;

import com.iris.generator.common.service.BaseService;
import com.iris.generator.entity.BaseClassEntity;
import com.iris.generator.common.page.PageResult;
import com.iris.generator.common.query.Query;

import java.util.List;

/**
 * 基类管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface BaseClassService extends BaseService<BaseClassEntity> {

    PageResult<BaseClassEntity> page(Query query);

    List<BaseClassEntity> getList();
}