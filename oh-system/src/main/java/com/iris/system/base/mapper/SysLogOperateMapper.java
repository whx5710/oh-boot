package com.iris.system.base.mapper;

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
public interface SysLogOperateMapper {

    List<SysLogOperateEntity> getList(SysLogOperateQuery query);

    int save(SysLogOperateEntity param);

}