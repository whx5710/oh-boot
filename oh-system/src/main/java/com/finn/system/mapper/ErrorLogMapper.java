package com.finn.system.mapper;

import com.finn.system.entity.ErrorLog;
import com.finn.framework.datasource.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 系统错误日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-04-29 18:38:22
 * 
 */
@Mapper
public interface ErrorLogMapper extends BaseMapper<ErrorLog> {

}
