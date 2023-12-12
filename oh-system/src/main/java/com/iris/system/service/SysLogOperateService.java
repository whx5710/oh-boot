package com.iris.system.service;

import com.iris.system.entity.SysLogOperateEntity;
import com.iris.system.query.SysLogOperateQuery;
import com.iris.system.vo.SysLogOperateVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SysLogOperateService extends BaseService<SysLogOperateEntity> {

    PageResult<SysLogOperateVO> page(SysLogOperateQuery query);
}