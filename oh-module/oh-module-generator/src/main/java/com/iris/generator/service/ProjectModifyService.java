package com.iris.generator.service;

import com.iris.generator.common.service.BaseService;
import com.iris.generator.entity.ProjectModifyEntity;
import com.iris.generator.common.page.PageResult;
import com.iris.generator.common.query.Query;

import java.io.IOException;

/**
 * 项目名变更
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface ProjectModifyService extends BaseService<ProjectModifyEntity> {

    PageResult<ProjectModifyEntity> page(Query query);

    byte[] download(ProjectModifyEntity project) throws IOException;

}