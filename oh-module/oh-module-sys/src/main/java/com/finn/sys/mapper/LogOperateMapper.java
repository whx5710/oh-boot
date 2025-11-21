package com.finn.sys.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.sys.entity.LogOperateEntity;
import com.finn.sys.query.LogOperateQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface LogOperateMapper extends BaseMapper<LogOperateEntity> {

    List<LogOperateEntity> getList(LogOperateQuery query);

    int save(LogOperateEntity param);

}