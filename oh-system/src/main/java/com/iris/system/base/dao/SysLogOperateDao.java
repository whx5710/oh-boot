package com.iris.system.base.dao;

import com.iris.framework.common.constant.Constant;
import com.iris.framework.datasource.annotations.Ds;
import com.iris.system.base.entity.SysLogOperateEntity;
import com.iris.system.base.query.SysLogOperateQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
@Ds(Constant.SYS_DB)
public interface SysLogOperateDao {

    List<SysLogOperateEntity> getList(SysLogOperateQuery query);

    int save(SysLogOperateEntity param);

}