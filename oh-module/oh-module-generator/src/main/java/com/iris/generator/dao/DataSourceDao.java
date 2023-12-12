package com.iris.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iris.generator.entity.DataSourceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface DataSourceDao extends BaseMapper<DataSourceEntity> {

}