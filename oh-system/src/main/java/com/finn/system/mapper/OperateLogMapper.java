package com.finn.system.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.system.entity.OperateLogEntity;
import com.finn.system.query.OperateLogQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 操作日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLogEntity> {

    List<OperateLogEntity> getList(OperateLogQuery query);

//    int save(LogOperateEntity param);

}